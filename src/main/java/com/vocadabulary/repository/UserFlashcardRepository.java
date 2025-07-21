import com.vocadabulary.model.UserFlashcard;
import com.vocadabulary.model.UserFlashcardKey;
import com.vocadabulary.model.FlashcardStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserFlashcardRepository extends JpaRepository<UserFlashcard, UserFlashcardKey> {
    List<UserFlashcard> findByUserIdAndStatus(Long userId, FlashcardStatus status);
}