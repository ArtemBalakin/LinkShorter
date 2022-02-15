package shorterURL.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shorterURL.coder.Coder;
import shorterURL.exception.ResourceNotFoundException;
import shorterURL.repositories.Shorter;
import shorterURL.repositories.ShorterRepository;
import shorterURL.validator.URLValidator;

import java.sql.Timestamp;

@Transactional
@Service
public class MainService {
    private final DateCalculator dateCalculator;
    private final ShorterRepository repository;
    private final Coder coder;
    private URLValidator urlValidator;

    @Autowired
    public MainService(DateCalculator dateCalculator, ShorterRepository repository, Coder coder, URLValidator urlValidator) {
        this.dateCalculator = dateCalculator;
        this.urlValidator = urlValidator;
        this.repository = repository;
        this.coder = coder;
    }

    public String makeShortLink(String originalLink, Long ttl, String ownTitle, Long pin) {
        System.out.println("title: " + ownTitle);
        String shortLink;
        if (ownTitle != null && !ownTitle.equals("") && repository.findByHash(ownTitle) != null)
            return "This name is exist";
        if (isLinkCorrect(originalLink)) {
            if (ownTitle == null || ownTitle.equals("")) {
                shortLink = foundLinkHash(originalLink);
                if (shortLink != null) return shortLink;
                else {
                    ownTitle = makeHash();
                    System.out.println("New: " + ownTitle);
                }
            }
            System.out.println(ownTitle);
            saveShortLink(buildShort(originalLink, ownTitle, ttl, pin));
            return ownTitle;
        }
        return "Invalid link";
    }

    private void saveShortLink(Shorter shorter) {
        repository.save(shorter);
    }

    private Shorter buildShort(String originalUrl, String shortUrl, Long TTL, Long pin) {
        return Shorter.builder()
                .originalUrl(originalUrl)
                .hash(shortUrl)
                .ttl(TTL)
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .lastRequest(new Timestamp(System.currentTimeMillis()))
                .code(pin)
                .build();

    }

    private boolean isLinkCorrect(String originalLink) {
        return urlValidator.checkURL(originalLink);
    }

    private String makeHash() {
        String shortLink = getFreeShortLink();
        while (repository.findByHash(shortLink) != null) {
            shortLink = getFreeShortLink();
        }
        return shortLink;
    }

    public String foundLinkHash(String originalLink) {
        Shorter shorter = repository.findFirstByOriginalUrl(originalLink);
        return shorter != null ? shorter.getHash() : null;
    }

    public String foundOriginalLinkByHash(String hash) throws ResourceNotFoundException {
        Shorter shorter = repository.findByHash(hash);
        if (shorter == null) throw new ResourceNotFoundException();
        if (shorter.getTtl() != null)
            if (shorter.getTtl() < dateCalculator.NumberOfMillisecondsUntilToday(shorter.getCreatedAt())) {
                repository.deleteByHash(hash);
                throw new ResourceNotFoundException();
            }
        if (shorter.getCount() == null) {
            repository.updateShort(1L, hash);
            repository.updateShort(new Timestamp(System.currentTimeMillis()), hash);
        } else {
            repository.updateShort(shorter.getCount() + 1, hash);
            repository.updateShort(new Timestamp(System.currentTimeMillis()), hash);
            if (shorter.getUniqCount() == null || 300000 < dateCalculator.NumberOfMillisecondsUntilToday(shorter.getLastRequest())) {
                if (shorter.getUniqCount() != null) {
                    repository.updateShortUniqCount(shorter.getUniqCount() + 1, hash);
                } else {
                    repository.updateShortUniqCount(1L, hash);
                }
            }
        }
        return shorter.getOriginalUrl();

    }

    public Shorter getStatistic(String hash) {
        return Shorter.getShorterWithoutCode(repository.findByHash(hash));
    }

    private String getFreeShortLink() {
        return coder.randomString();
    }

    @PreAuthorize("hasAuthority('USER')")
    public String deleteShortLink(String hash, Long pin) {
        System.out.println(hash + " " + pin);
        repository.deleteByHashAndCode(hash, pin);
        return "Срок жизни истек";
    }
}
