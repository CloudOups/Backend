package tn.esprit.pi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.pi.entities.CodePromo;

import java.util.List;

public interface CodePromoRepository extends JpaRepository<CodePromo,Long> {

    @Query("SELECT cp FROM CodePromo cp WHERE cp.user.id = :userId " +
            "AND FUNCTION('YEAR', cp.dateExpCodePromo) = :year " +
            "AND FUNCTION('MONTH', cp.dateExpCodePromo) = :month " +
            "AND cp.dateExpCodePromo > CURRENT_TIMESTAMP")
    List<CodePromo> findPromoCodesByUserIdAndMonth(@Param("userId") Integer userId,
                                                        @Param("year") int year,
                                                        @Param("month") int month);

    CodePromo findValidPromoCodesByUserId(int iduser);
    CodePromo findByCodeAndEtatCodePromo(String code,Boolean bool);
}
