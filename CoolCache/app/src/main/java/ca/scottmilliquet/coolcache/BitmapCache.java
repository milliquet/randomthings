package ca.scottmilliquet.coolcache;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.io.File;

/**
 * Created by scottmilliquet on 14-12-21.
 *
 * A Class to load bitmaps and cache them.
 *
 * Note in real life bitmaps should be loaded asynchronously as shown here http://developer.android.com/training/displaying-bitmaps/process-bitmap.html
 * and the max size of the output image should be supported as well and could easily be done from here http://developer.android.com/training/displaying-bitmaps/load-bitmap.html
 */
public class BitmapCache {

    private PriorityQueue<QueueObject> priorityQueue; //used to keep track of which object to flush first
    private HashMap<String, QueueObject> hashMap; //used to lookup the object stored in the PriorityQueue;
    private int maxNumberOfBitmaps;
    private final static int DEFAULT_MAX_SIZE = 4; //small default size for sample purposes

    private static final String TAG = "coolcache.ImageCache";

    public BitmapCache(){
        this(DEFAULT_MAX_SIZE);
    }

    public BitmapCache(int maxNumberOfBitmaps) {
        this.maxNumberOfBitmaps = maxNumberOfBitmaps;
        priorityQueue = new PriorityQueue<>(16, new QueueObjectTimeComparator());
        hashMap = new HashMap<>();
    }


    /**
     * Loads a bitmap from the provided imageLocation and puts it in the cache
     *
     *
     * @param imageLocation the file url of the image in the form /sdcard/Pictures/filename.jpg
     * @throws java.lang.IllegalArgumentException if the imageLocation is null or length 0
     * @throws java.io.FileNotFoundException if the imageLocation is not found
     * @throws java.lang.OutOfMemoryError if there isnt memory to decode the bitmap
     *
     */
    public Bitmap getBitmap(String imageLocation) throws IllegalArgumentException, FileNotFoundException, OutOfMemoryError {

        if ( imageLocation == null || imageLocation.length() == 0) {
            throw new IllegalArgumentException();
        }

        //if its in the cache return it
        QueueObject qo = hashMap.get(imageLocation);

        //we have the bitmap, update the accessed time and return the bitmap
        if ( qo != null ){

            Log.d(TAG, imageLocation + " exists in the cache");

            //if we access the object we need to update its accessed time,  this requires removal and addition to the
            //priority queue to ensure we know which item to remove is up to date
            //note there is no need to update the hashmap as we keep the same QueueObject
            priorityQueue.remove(qo);
            qo.timeAccessed = System.currentTimeMillis();
            priorityQueue.add(qo);


            return qo.bitmap;
        }

        File imgFile = new File(imageLocation);

        if(imgFile.exists()){
            if ( priorityQueue.size() + 1 > maxNumberOfBitmaps ) {
                removeOldestItem();
            }

            //build the bitmap and add it to the cache
            Bitmap b = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            if ( b != null) {
                QueueObject newQo = new QueueObject(imageLocation, b, System.currentTimeMillis());

                priorityQueue.add(newQo);
                hashMap.put(imageLocation, newQo);

            }
            return b;

        } else {
            throw new FileNotFoundException(imageLocation + " not found.");
        }
    }

    //utility function to remove the oldest item from the cache
    private void removeOldestItem() {
        QueueObject toRemove = priorityQueue.peek();
        if (toRemove != null) {
            Log.d(TAG, "Removing: " + toRemove);
            priorityQueue.remove(toRemove);
            hashMap.remove(toRemove.imageLocation);
        }
    }

    /**
     * Function to flush the cache.
     */
    public void flush(){
        Log.d(TAG, "Flushing cache");
        hashMap.clear();
        priorityQueue.clear();
    }

    /**
     * Function to print out the contents of the cache
     */
    public void printCacheState() {
        Log.d(TAG, "START CACHE STATE");

        for(QueueObject qo : priorityQueue) {
            Log.d(TAG, "PQ: " + qo);
        }

        for(QueueObject qo : hashMap.values()) {
            Log.d(TAG, "HM: " + qo);
        }

        Log.d(TAG, "END CACHE STATE");
    }

    /**
     * Internal class used to keep relevant information about objects that are cached.
     */
    private class QueueObject {

        String imageLocation;
        Bitmap bitmap;
        long timeAccessed;

        QueueObject( String imageLocation, Bitmap bitmap, long timeAccessed ){
            this.imageLocation = imageLocation;
            this.bitmap = bitmap;
            this.timeAccessed = timeAccessed;
        }

        /**
         *
         * @return nicely formatted string representation of QueueObject
         */
        public String toString() {
            StringBuilder out = new StringBuilder();
            out.append(imageLocation).append(' ').append('/').append(' ');
            out.append(timeAccessed).append(' ').append('/').append(' ');
            out.append(bitmap.getWidth()).append('x').append(bitmap.getHeight());
            return out.toString();
        }

    }

    /**
     * Comparator to compare QueueObjects based on their timeAccessed field
     */
    private class QueueObjectTimeComparator implements Comparator<QueueObject> {

        QueueObjectTimeComparator(){
            super();
        }

        public int compare( QueueObject a, QueueObject b) {
            return (int) (a.timeAccessed - b.timeAccessed);
        }
    }
}
