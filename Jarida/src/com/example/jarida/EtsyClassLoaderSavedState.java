package com.example.jarida;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * A {@link android.os.Parcelable} implementation that should be used by inheritance
 * hierarchies to ensure the state of all classes along the chain is saved.
 */
public abstract class EtsyClassLoaderSavedState implements Parcelable {
    public static final EtsyClassLoaderSavedState EMPTY_STATE = new EtsyClassLoaderSavedState() {};

    private Parcelable mSuperState = EMPTY_STATE;
    private ClassLoader mClassLoader;

    /**
     * Constructor used to make the EMPTY_STATE singleton
     */
    private EtsyClassLoaderSavedState() {
        mSuperState = null;
        mClassLoader = null;
    }

    /**
     * Constructor called by derived classes when creating their ListSavedState objects
     *
     * @param superState The state of the superclass of this view
     */
    protected EtsyClassLoaderSavedState(Parcelable superState, ClassLoader classLoader) {
        mClassLoader = classLoader;
        if (superState == null) {
            throw new IllegalArgumentException("superState must not be null");
        }
        else {
            mSuperState = superState != EMPTY_STATE ? superState : null;
        }
    }

    /**
     * Constructor used when reading from a parcel. Reads the state of the superclass.
     *
     * @param source
     */
    protected EtsyClassLoaderSavedState(Parcel source) {
        // ETSY : we're using the passed super class loader unlike AbsSavedState
        Parcelable superState = source.readParcelable(mClassLoader);
        mSuperState = superState != null ? superState : EMPTY_STATE;
    }

    final public Parcelable getSuperState() {
        return mSuperState;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mSuperState, flags);
    }

    public static final Parcelable.Creator<EtsyClassLoaderSavedState> CREATOR
            = new Parcelable.Creator<EtsyClassLoaderSavedState>() {

        public EtsyClassLoaderSavedState createFromParcel(Parcel in) {
            Parcelable superState = in.readParcelable(null);
            if (superState != null) {
                throw new IllegalStateException("superState must be null");
            }
            return EMPTY_STATE;
        }

        public EtsyClassLoaderSavedState[] newArray(int size) {
            return new EtsyClassLoaderSavedState[size];
        }
    };
}
