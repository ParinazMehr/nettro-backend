package com.taxikar.bean.request;

import java.sql.Timestamp;

public class FavoritesRequest
{
    private String favoriteName;
    private String favoritePos;

    public FavoritesRequest()
    {
    }

    public FavoritesRequest(String favoriteName, String favoritePos, Timestamp favoriteTimeStamp)
    {
        this.favoriteName = favoriteName;
        this.favoritePos = favoritePos;

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


}
