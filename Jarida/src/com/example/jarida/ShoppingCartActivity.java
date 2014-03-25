package com.example.jarida;

import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;
import de.ronnyfriedland.shoppinglist.entity.Entry;
import de.ronnyfriedland.shoppinglist.entity.Shoppinglist;

/**
 * @author Ronny Friedland
 */
@SuppressWarnings("unchecked")
public class ShoppingCartActivity extends SherlockActivity {
	// constants
    private static final String CURRENT_TAB = "currenttab";

    // gestures
    private GestureDetector gestureScanner;
    private GestureLibrary gestureLib;

    // notification
    private NotificationManager notificationManager;

    // handler
    private final Handler handler = new Handler();

    // common (tab)
    private transient TabHost tabHost;
    // tab1
    private transient ListView listView;
    private transient GestureOverlayView gestureOverlayTab1;
    // tab2
    //private transient Button saveButton;
    private transient GestureOverlayView gestureOverlayTab2;

    private void initListTabData() {
        //Shoppinglist list = DataSource.getInstance(getBaseContext()).getList();
        //Log.v("Items in shopping list", list.toString());
        
        List<Entry> entries = DataSource.getInstance(getBaseContext()).getEntries();
        //Log.v("Items in entries", entries.toString());
       
       /* if (null == list) {
            list = new Shoppinglist(0);
            DataSource.getInstance(getBaseContext()).createList(list);
        }*/
        
        ((ShoppingListAdapter<Entry>) listView.getAdapter()).addAll(entries);
        ((ShoppingListAdapter<Entry>) listView.getAdapter()).notifyDataSetChanged();
    }

    private void configureNewEntryView() {
        //Collection<String> list = ReadXMLFile.parseFile(getResources().getString(R.raw.description));
        
    	/*saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                Toast.makeText(getBaseContext(), getResources().getString(R.string.createSuccess), Toast.LENGTH_LONG)
                        .show();

                ((ShoppingListAdapter<Entry>) listView.getAdapter()).notifyDataSetChanged();
            }
        });
*/


        gestureOverlayTab2.addOnGesturePerformedListener(new ShoppingListGestureListener());
    }

    private void configureListView() {
    	
        ShoppingListAdapter<Entry> myAdapter = new ShoppingListAdapter<Entry>(getBaseContext(),
        		R.layout.row_shoppinglist_item);
        listView.setAdapter(myAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(new ShoppingListClickListener());
        registerForContextMenu(listView);

        gestureOverlayTab1.addOnGesturePerformedListener(new ShoppingListGestureListener());
    }

    private void configureTabs() {
        tabHost.setup();
        TabSpec spec1 = tabHost.newTabSpec(getResources().getString(R.string.list));
        spec1.setContent(R.id.tab1);
        spec1.setIndicator(getResources().getString(R.string.list));

        TabSpec spec2 = tabHost.newTabSpec(getResources().getString(R.string.checkout));
        spec2.setIndicator(getResources().getString(R.string.checkout));
        spec2.setContent(R.id.tab2);

        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
    }

    private void clearItems() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog dialog = builder.setMessage(R.string.confirmDelete)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int id) {
                        if (Log.isLoggable(getClass().getSimpleName(), Log.DEBUG)) {
                            Log.d(getClass().getSimpleName(), "Clear list");
                        }
                        Shoppinglist list = DataSource.getInstance(getBaseContext()).getList();
                        DataSource.getInstance(getBaseContext()).deleteEntry(list);
                        DataSource.getInstance(getBaseContext()).deleteList();
                        ((ShoppingListAdapter<Entry>) listView.getAdapter()).clear();
                        ((ShoppingListAdapter<Entry>) listView.getAdapter()).notifyDataSetChanged();

                        Toast.makeText(getBaseContext(), getResources().getString(R.string.deleteSuccess),
                                Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int id) {
                        if (Log.isLoggable(getClass().getSimpleName(), Log.DEBUG)) {
                            Log.d(getClass().getSimpleName(), "Clear list canceled");
                        }
                    }
                }).create();
        dialog.show();
    }

    private void clearFinishedItems() {
        List<Entry> entries = new ArrayList<Entry>();
        for (int i = 0; i < listView.getAdapter().getCount(); i++) {
            Entry entry = (Entry) listView.getAdapter().getItem(i);
            DataSource.getInstance(getBaseContext()).deleteEntry(entry);
        }
        if (0 == entries.size()) {
            DataSource.getInstance(getBaseContext()).deleteList();
        }
        ((ShoppingListAdapter<Entry>) listView.getAdapter()).clear();
        ((ShoppingListAdapter<Entry>) listView.getAdapter()).addAll(entries);
        ((ShoppingListAdapter<Entry>) listView.getAdapter()).notifyDataSetChanged();

        Toast.makeText(getBaseContext(), getResources().getString(R.string.clearEntriesSuccess), Toast.LENGTH_LONG)
                .show();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoppinglist_layout);
        
        final ActionBar ab = getSupportActionBar();
        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);

        //saveButton = (Button) findViewById(R.id.buttonSave);
        listView = (ListView) findViewById(R.id.listViewList);
        tabHost = (TabHost) findViewById(R.id.tabHost);
        gestureOverlayTab1 = (GestureOverlayView) findViewById(R.id.gestureOverlayTab1);
        gestureOverlayTab2 = (GestureOverlayView) findViewById(R.id.gestureOverlayTab2);

        gestureLib = GestureLibraries.fromRawResource(getBaseContext(), R.raw.gestures);
        gestureLib.load();

        notificationManager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);

        configureTabs();
        configureListView();
        configureNewEntryView();

        initListTabData();
        initTimer();
    }
    private void initTimer() {
        final long delay = 1000 * 60 * 60 * 24; // every day
        Runnable timer = new Runnable() {
            /**
             * {@inheritDoc}
             * 
             * @see java.lang.Runnable#run()
             */
            @Override
            public void run() {
               /* int importantCount = 0;
                List<Entry> entries = ShoppingListDataSource.getInstance(getBaseContext()).getEntries();
                for (Entry entry : entries) {
                    if (entry.getImportant() && Status.OPEN.equals(entry.getStatus())) {
                        importantCount++;
                    }
                }
                if (0 < importantCount) { // only if important entries found
                    PendingIntent contentIntent = PendingIntent.getActivity(getBaseContext(), 0, getIntent(), 0);
                    Notification notification = new NotificationCompat.Builder(getBaseContext())
                            .setContentTitle(getResources().getString(R.string.app_name))
                            .setContentText(getResources().getString(R.string.notificationImportantEntryCount))
                            .setSmallIcon(R.drawable.ic_launcher).setContentIntent(contentIntent)
                            .setOnlyAlertOnce(true).build();
                    notification.flags = Notification.DEFAULT_LIGHTS | Notification.FLAG_AUTO_CANCEL;
                    notificationManager.notify(0, notification);
                    handler.postDelayed(this, delay);
                }*/
            }
        };
        handler.postDelayed(timer, delay);
    }

    /**
     * {@inheritDoc}
     * 
     * @see android.app.Activity#onTouchEvent(android.view.MotionEvent)
     */
    @Override
    public boolean onTouchEvent(final MotionEvent me) {
        boolean ret = false;
        try {
            ret = gestureScanner.onTouchEvent(me);
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Got exception on touch event.", e);
        }
        return ret;
    }

    /**
     * {@inheritDoc}
     * 
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */

    @Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// TODO Auto-generated method stub
    	
    	menu.add("clearList").setIcon(R.drawable.ic_cancel);
    	menu.add("clearEntries").setIcon(R.drawable.ic_link);
    	menu.add(getBaseContext().getResources().getString(R.id.exit)).setIcon(R.drawable.ic_magnifying_glass);

		return super.onCreateOptionsMenu(menu);
	}

	/**
     * {@inheritDoc}
     * 
     * @see android.app.Activity#onDestroy()
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * {@inheritDoc}
     * 
     * @see android.app.Activity#onCreateContextMenu(android.view.ContextMenu,
     *      android.view.View, android.view.ContextMenu.ContextMenuInfo)
     */
    @Override
    public void onCreateContextMenu(final ContextMenu menu, final View v, final ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        // menu.setHeaderTitle("Context Menu");
        menu.add(0, v.getId(), 0, getResources().getString(R.string.show));
        menu.add(0, v.getId(), 1, getResources().getString(R.string.edit));
        menu.add(0, v.getId(), 2, getResources().getString(R.string.delete));
    }


    /**
     * {@inheritDoc}
     * 
     * @see android.app.Activity#onContextItemSelected(android.view.MenuItem)
     */
    
    
    
    
    
    @Override
    public boolean onContextItemSelected(final MenuItem item) {
        boolean result = false;
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Entry entry = (Entry) listView.getAdapter().getItem(info.position);
        if (item.getTitle() == getResources().getString(R.string.delete)) {
            if (Log.isLoggable(getClass().getSimpleName(), Log.DEBUG)) {
                Log.d(getClass().getSimpleName(), String.format("Delete entry with id %s.", entry.getUuid()));
            }

            DataSource.getInstance(getBaseContext()).deleteEntry(entry);
            ((ShoppingListAdapter<Entry>) listView.getAdapter()).remove(entry);
            ((ShoppingListAdapter<Entry>) listView.getAdapter()).notifyDataSetChanged();

            result = true;
        } /*else if (item.getTitle() == getResources().getString(R.string.show)) {
            if (Log.isLoggable(getClass().getSimpleName(), Log.DEBUG)) {
                Log.d(getClass().getSimpleName(), String.format("Show image for entry with id %s.", entry.getUuid()));
            }
            byte[] image = entry.getImage();

            if (null != image) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                ImageView pictureView = new ImageView(getBaseContext());
                pictureView.setScaleType(ScaleType.FIT_XY);
                pictureView.setImageBitmap(bitmap);
                pictureView.setMinimumWidth(bitmap.getHeight());
                pictureView.setMinimumHeight(bitmap.getWidth());
                // build dialog
                Builder builder = new AlertDialog.Builder(this);
                builder.setView(pictureView);
                // show dialog
                Dialog dialog = builder.create();
                dialog.show();
                result = true;
            } else {
                Toast.makeText(getBaseContext(), R.string.noImage, Toast.LENGTH_SHORT).show();
                result = false;
            }
        }*/
        return result;
    }

    @Override
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
    	 boolean ret;
         switch (item.getItemId()) {
         case R.id.exit:
             finish();
             ret = true;
             break;
         case R.id.clearlistItems: {
             clearItems();
             ret = true;
         }
             break;
         case R.id.clearFinishedItems: {
             clearFinishedItems();
             ret = true;
         }
             break;
         default:
             ret = super.onOptionsItemSelected(item);
             break;
         }
         return ret;
		
	}

	/**
     * {@inheritDoc}
     * 
     * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
     */
    @Override
    public void onSaveInstanceState(final Bundle savedInstanceState) {
        savedInstanceState.putInt(CURRENT_TAB, tabHost.getCurrentTab());
    }

    /**
     * {@inheritDoc}
     * 
     * @see android.app.Activity#onRestoreInstanceState(android.os.Bundle)
     */
    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        tabHost.setCurrentTab(savedInstanceState.getInt(CURRENT_TAB));
    }
    /**
     * Listener to track clicks on the list view.
     * 
     * @author Ronny Friedland
     */
    class ShoppingListClickListener implements OnItemClickListener {
        /**
         * {@inheritDoc}
         * 
         * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView,
         *      android.view.View, int, long)
         */
        @Override
        public void onItemClick(final AdapterView<?> adapterView, final View view, final int pos, final long id) {
            /*ListView list = (ListView) adapterView;
            TextView textView = (TextView) view;

            Entry entry = (Entry) list.getAdapter().getItem(pos);
            if (ADDEDTOCART.OPEN == entry.getStatus()) {
                UIHelper.toggleStrikeThrough(getBaseContext(), textView, false);
                entry.setStatus(ADDEDTOCART.FINISHED);
            } else {
                UIHelper.toggleStrikeThrough(getBaseContext(), textView, true);
                entry.setStatus(ADDEDTOCART.OPEN);
            }
            DataSource.getInstance(getBaseContext()).updateEntry(entry);*/
        }
    }

    /**
     * @author Ronny Friedland
     */
    class ShoppingListGestureListener implements OnGesturePerformedListener {
        @Override
        public void onGesturePerformed(final GestureOverlayView overlay, final Gesture gesture) {
            ArrayList<Prediction> predictions = gestureLib.recognize(gesture);
            if (predictions.size() > 0 && predictions.get(0).score > 1.0) {
                String result = predictions.get(0).name;
                if ("clearall".equalsIgnoreCase(result)) {
                    int currentTab = tabHost.getCurrentTab();
                    if (0 == currentTab) {
                        clearItems();
                    }
                } else if ("clearfinished".equalsIgnoreCase(result)) {
                    int currentTab = tabHost.getCurrentTab();
                    if (0 == currentTab) {
                        clearFinishedItems();
                    }
                } else {
                    // move in left
                    TranslateAnimation animLeftIn = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 1.0f,
                            Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                            Animation.RELATIVE_TO_PARENT, 0.0f);
                    animLeftIn.setDuration(180);
                    animLeftIn.setInterpolator(new AccelerateInterpolator());
                    // move out left
                    TranslateAnimation animLeftOut = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
                            Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                            Animation.RELATIVE_TO_PARENT, 0.0f);
                    animLeftOut.setDuration(180);
                    animLeftOut.setInterpolator(new AccelerateInterpolator());
                    // move in right
                    TranslateAnimation animRightIn = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1.0f,
                            Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                            Animation.RELATIVE_TO_PARENT, 0.0f);
                    animRightIn.setDuration(180);
                    animRightIn.setInterpolator(new AccelerateInterpolator());
                    // move out right
                    TranslateAnimation animRightOut = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
                            Animation.RELATIVE_TO_PARENT, 1.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                            Animation.RELATIVE_TO_PARENT, 0.0f);
                    animRightOut.setDuration(180);
                    animRightOut.setInterpolator(new AccelerateInterpolator());

                    if ("moveleft".equalsIgnoreCase(result)) {
                        int currentTab = tabHost.getCurrentTab();
                        int childCount = tabHost.getTabWidget().getChildCount();
                        View currentView = tabHost.getCurrentView();
                        if (currentTab > 0) {
                            tabHost.setCurrentTab(currentTab - 1);
                        } else {
                            tabHost.setCurrentTab(childCount - 1);
                        }
                        View nextView = tabHost.getCurrentView();
                        currentView.setAnimation(animLeftOut);
                        nextView.setAnimation(animLeftIn);
                    } else if ("moveright".equalsIgnoreCase(result)) {
                        int currentTab = tabHost.getCurrentTab();
                        int childCount = tabHost.getTabWidget().getChildCount();
                        View currentView = tabHost.getCurrentView();
                        if (childCount - 1 > currentTab) {
                            tabHost.setCurrentTab(currentTab + 1);
                        } else {
                            tabHost.setCurrentTab(0);
                        }
                        View nextView = tabHost.getCurrentView();
                        currentView.setAnimation(animRightOut);
                        nextView.setAnimation(animRightIn);
                    }
                }
            }
        }
    }
}
