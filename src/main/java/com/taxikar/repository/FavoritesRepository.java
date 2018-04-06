package com.taxikar.repository;

import com.taxikar.entity.Favorites;
import com.taxikar.entity.JurneyDriver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FavoritesRepository extends JpaRepository<Favorites, Long>
{
    @Query("select jr from Favorites jr where jr.id=?1")
    Favorites getOneFavorite(String id);

    @Query("select jr from Favorites jr where jr.userId=?1")
    List<Favorites> getAllFavorites(String userID);
}
