package com.example.thymeleaf.skierowanie.dao;

import com.example.thymeleaf.skierowanie.model.SkierowanieDoLekarza;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SkierowanieDoLekarzaDao extends CrudRepository<SkierowanieDoLekarza, Integer> {

    @Override
    List<SkierowanieDoLekarza> findAll();
    //metody które nadpisujemy są zawarte w bibliotece Crude


    @Override
    List<SkierowanieDoLekarza> findAllById(Iterable<Integer> integers);

    // spring umożliwia pisanie zapytan do bazy danych bez znajmości sql'a'


    List<SkierowanieDoLekarza> findAllByPacjentAndTerminOrderById(String pacjent, Date termin);

    List<SkierowanieDoLekarza> findAllByOrderById();

    // nasze wlasne query "SkierowanieDoLekarza" -> taka encja została przez nas zdefiinowana i na raazi tylko ta jednaq
    @Query("select test from SkierowanieDoLekarza test order by test.termin desc")
    List<SkierowanieDoLekarza> test();

    @Query("select test from SkierowanieDoLekarza test where test.pacjent = :pacjent")
    List<SkierowanieDoLekarza> test123(String pacjent);

    //wszystkie skieroania  dla lekarza posortowane po pacjencie rosnaco
    /*:pacjent -> jest argumentem, ten argument wchodzi w SkierowanieController
     * jako argument w funkcji test3 -> skierowanieDoLekarzaDao.cwiczenie("Wiliam") * */
    @Query("select test from SkierowanieDoLekarza test where test.lekarz=:lekarz order by test.pacjent asc")
    List<SkierowanieDoLekarza> cwiczenie(String lekarz);
}
