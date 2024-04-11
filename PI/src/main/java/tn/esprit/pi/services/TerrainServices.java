package tn.esprit.pi.services;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.pi.entities.ReservationTerrain;
import tn.esprit.pi.entities.StatusTerrain;
import tn.esprit.pi.entities.Terrain;
import tn.esprit.pi.repositories.IReservationTerrRepository;
import tn.esprit.pi.repositories.ITerrainRepository;

import java.util.Collection;
import java.util.List;
@Service
@AllArgsConstructor
public class TerrainServices implements ITerrainServices{
    ITerrainRepository terrainRepository;
    IReservationTerrRepository reservationTerrRepository;
    @Override
    public Terrain addTerrain(Terrain terrain) {

        return terrainRepository.save(terrain);
    }

    @Override
    public Terrain updateTerrain(Terrain terrain) {
        return terrainRepository.save(terrain);
    }

    @Override
    public void delete(Long numterrain) {
terrainRepository.deleteById(numterrain);
    }

    @Override
    public Terrain getById(Long numterrain) {
        return terrainRepository.findById(numterrain).get();
    }
    @Override
    public List<Terrain> getByEtat(String statusTerrain){
        StatusTerrain status = StatusTerrain.valueOf(statusTerrain);
        List<Terrain> result = terrainRepository.findByStatusTerrain(status);
        return result;
    };

    @Override
    public List<Terrain> getAll() {
        return( List<Terrain>) terrainRepository.findAll();
    }
    @Override
    @Scheduled(cron ="*/60 * * * * *")
    public void getExpiredRes() {
        List<ReservationTerrain> expiredReservations = reservationTerrRepository.findByExpired();

        for (ReservationTerrain reservation : expiredReservations) {
            // Update reservation state to false
            reservation.setEtatReser(false);
            reservationTerrRepository.save(reservation);

            // Free up the associated terrain
            Terrain terrain = reservation.getTerrain();
            terrain.setStatusTerrain(StatusTerrain.valueOf("Libre"));
            terrainRepository.save(terrain);

            System.out.println("Reservation expired: " + reservation.getNumRes());
        }
    }
}
