package com.adi.ListBuster;

import android.app.ListActivity;
import android.os.Bundle;

public class ListBusterActivity extends ListActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    
    /**add the set on click listener that instantiates the other activity 
     * that displays the album cover and the album info as the album cover
     * from a gallery. This is how a workflow must proceed -
     * 1. User launches activity, sees the list containing all his songs
     * 2. Whrn the list acitivity is launched the list is populated with the data
     *    that is retrieved from the android local store.
     * 3. When the user clicks on a particular song the osng detail acticity
     *    is launched.
     * 4. Before launching the song detail activity , the server is queried in an 
     *    async background thread that is started as soon as the activity transition
     *    starts so that while the next acctivity is being created the server fetching
     *    is done. 
     * 5. When the next activity is initialized it displays the following - 
     *    -song name in the centre
     *    -a gallery view with the centre image set to the same image that was displayed in the 
     *    list view. the other images in the gallery show the fetched options from the server.
     *    -the text view contains the aritst bio, song info, album info, other tracks in the album.
     * 6. The set this as cover button is activated if the gallery selection is changed from the 
     *    initial gallery selection when the activity was started , otherwise it is inactive.
     * 7. The retrieved server datais parsed using a parser(last FM or any other). The parser returns 
     * 	  a song object that is used to populate the controls.
     * 8. All the persistent data storage needs to be managed in the activity life
     *    cycle callbacks
     * 
     */
    
    
}