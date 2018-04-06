package com.taxikar.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = "favorites")
public class Favorites
{
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(columnDefinition = "char(36)")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "FAVORITE_NAME")
    private String favoriteName;
    @Column(name = "FAVORITE_POS")
    private String favoritePos;

    @Column(name = "FAVORITE_TIMESTAMP")
    private Timestamp favoriteTimeStamp;
    @Column(name = "FAVORITE_NUMBER_OF_USES")
    private String favoriteNumberOFUses;


    public Favorites()
    { }
    public Favorites(String userId, String favoriteName, String favoritePos, Timestamp favoriteTimeStamp, String favoriteNumberOFUses)
    {
        this.userId = userId;
        this.favoriteName = favoriteName;
        this.favoritePos = favoritePos;
        this.favoriteTimeStamp = favoriteTimeStamp;
        this.favoriteNumberOFUses = favoriteNumberOFUses;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getFavoriteName()
    {
        return favoriteName;
    }

    public void setFavoriteName(String favoriteName)
    {
        this.favoriteName = favoriteName;
    }

    public String getFavoritePos()
    {
        return favoritePos;
    }

    public void setFavoritePos(String favoritePos)
    {
        this.favoritePos = favoritePos;
    }

    public Timestamp getFavoriteTimeStamp()
    {
        return favoriteTimeStamp;
    }

    public void setFavoriteTimeStamp(Timestamp favoriteTimeStamp)
    {
        this.favoriteTimeStamp = favoriteTimeStamp;
    }

    public String getFavoriteNumberOFUses()
    {
        return favoriteNumberOFUses;
    }

    public void setFavoriteNumberOFUses(String favoriteNumberOFUses)
    {
        this.favoriteNumberOFUses = favoriteNumberOFUses;
    }
}
