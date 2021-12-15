package com.udacity.asteroidradar.main

import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.repository.AsteroidRepository

class MainViewModel : ViewModel() {
//    private val asteroidRepository = AsteroidRepository()
//    fun getAsteroidsFeedList() = asteroidRepository.getAsteroidsFeedList()
//    suspend fun getAsteroidImageOfTheDayLiveData() = asteroidRepository.getAsteroidImageOfTheDayLiveData()
}

//class MainViewModel : ViewModel() {
//    private var asteroids: LiveData<List<Asteroid>>? = null
//    private val asteroidRepository = AsteroidRepository()
//
//    private fun createBookmarkMarkerObserver() {
//        // 1
//        mapsViewModel.getBookmarkMarkerViews()?.observe(
//            this, {
//// 2
//                map.clear()
//// 3
//                it?.let {
//                    displayAllBookmarks(it)
//                }
//            }) }
//
//    private fun mapBookmarksToMarkerView() {
//        asteroids = Transformations.map(bookmarkRepo.allBookmarks)
//        { repoBookmarks ->
//            repoBookmarks.map { bookmark ->
//                bookmarkToMarkerView(bookmark)
//            }
//        } }
//
//    fun getBookmarkMarkerViews() :
//            LiveData<List<BookmarkMarkerView>>? {
//        if (bookmarks == null) {
//            mapBookmarksToMarkerView()
//        }
//        return bookmarks
//    }
//
//    private val asteroidRepository: AsteroidRepository = AsteroidRepository()
//    fun addBookmarkFromPlace(place: Place, image: Bitmap?) {
//        // 4
//        val bookmark = bookmarkRepo.createBookmark()
//
//        bookmark.placeId = place.id
//        bookmark.name = place.name.toString()
//        bookmark.longitude = place.latLng?.longitude ?: 0.0
//        bookmark.latitude = place.latLng?.latitude ?: 0.0
//        bookmark.phone = place.phoneNumber.toString()
//        bookmark.address = place.address.toString()
//        // 5
//        val newId = bookmarkRepo.addBookmark(bookmark)
//    }
//}