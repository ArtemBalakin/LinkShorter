package shorterURL.repositories;

import com.sun.istack.NotNull;
import lombok.*;
import org.checkerframework.checker.index.qual.Positive;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class Shorter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    @NotNull
    private String hash;
    @Column(name = "original_url")
    @NotNull
    private String originalUrl;
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "TTL")
    @Positive
    private Long ttl;
    @Column(name = "count")
    private Long count;
    @Column(name = "uniq_count")
    private Long uniqCount;
    @Column(name = "last_request")
    private Timestamp lastRequest;
    @Column(name = "pin")
    private Long code;

    public static Shorter getShorterWithoutCode(Shorter shorter) {
        Shorter shorter1 = Shorter.builder()
                .originalUrl(shorter.getOriginalUrl())
                .uniqCount(shorter.getUniqCount())
                .count(shorter.getCount())
                .hash(shorter.getHash())
                .ttl(shorter.getTtl())
                .createdAt(shorter.getCreatedAt())
                .lastRequest(shorter.getLastRequest())
                .build();
        return shorter1;
    }
}
