package shorterURL.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface ShorterRepository extends JpaRepository<Shorter, Long> {
    Shorter findByHash(String hash);

    Integer deleteByHashAndCode(String hash, Long code);

    Integer deleteByHash(String hash);

    Shorter findFirstByOriginalUrl(String originalUrl);

    @Modifying
    @Query("update Shorter u set u.count = ?1  where u.hash = ?2")
    void updateShort(Long count, String hash);

    @Modifying
    @Query("update Shorter u set u.lastRequest = ?1  where u.hash = ?2")
    void updateShort(Timestamp timestamp, String hash );
    @Modifying
    @Query("update Shorter u set u.uniqCount = ?1  where u.hash = ?2")
    void updateShortUniqCount(Long uniqCount, String hash);
}
