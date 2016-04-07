package ru.testproject.blumental.artists.model;

import android.content.Context;

import java.util.List;

import ru.testproject.blumental.artists.model.data.ArtistDTO;
import rx.Observable;

/**
 * Created by Maxim Blumental on 3/25/2016.
 * bvmaks@gmail.com
 */
public interface Model {
    Observable<Integer> downloadPage(Context context, List<ArtistDTO> artistDTOs);
}
