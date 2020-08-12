package no.nordicsemi.android.mesh.transport;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import no.nordicsemi.android.mesh.opcodes.ApplicationMessageOpCodes;
import no.nordicsemi.android.mesh.utils.MeshAddress;

/**
 * To be used as a wrapper class when creating a GenericAdminPropertiesGet message.
 */
@SuppressWarnings("unused")
public class GenericAdminPropertyStatus extends GenericStatusMessage implements Parcelable {
    private static final String TAG = GenericAdminPropertyStatus.class.getSimpleName();
    private static final int OP_CODE = ApplicationMessageOpCodes.GENERIC_ADMIN_PROPERTY_STATUS;

    private short mPropertyID;
    private byte mUserAccess;
    private byte[] mPropertyValue;

    private static final Parcelable.Creator<GenericAdminPropertyStatus> CREATOR = new Parcelable.Creator<GenericAdminPropertyStatus>() {
        @Override
        public GenericAdminPropertyStatus createFromParcel(Parcel in) {
            final AccessMessage message = in.readParcelable(AccessMessage.class.getClassLoader());
            //noinspection ConstantConditions
            return new GenericAdminPropertyStatus(message);
        }

        @Override
        public GenericAdminPropertyStatus[] newArray(int size) {
            return new GenericAdminPropertyStatus[size];
        }
    };

    /**
     * Constructs the GenericAdminPropertyStatus mMessage.
     *
     * @param message Access Message
     */
    public GenericAdminPropertyStatus(@NonNull final AccessMessage message) {
        super(message);
        this.mParameters = message.getParameters();
        parseStatusParameters();
    }

    @Override
    void parseStatusParameters() {
        Log.v(TAG, "Received generic admin property status from: " + MeshAddress.formatAddress(mMessage.getSrc(), true));
        final ByteBuffer buffer = ByteBuffer.wrap(mParameters).order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);
        mPropertyID = buffer.getShort();
        mUserAccess = buffer.get();
        int count = buffer.limit() - 3;
        mPropertyValue = new byte[count];
        for (int i = 0; i < count; i++) {
            mPropertyValue[i] = buffer.get();
        }
    }

    @Override
    int getOpCode() {
        return OP_CODE;
    }

    /**
     * Returns the property ID of the GenericAdminPropertyStatus
     *
     * @return property ID
     */
    public final short getPropertyID() {
        return mPropertyID;
    }

    /**
     * Returns the user access of the GenericAdminPropertyStatus
     *
     * @return access level
     */
    public final byte getAccessLevel() {
        return mUserAccess;
    }

    /**
     * Returns the value of the GenericAdminPropertyStatus
     *
     * @return value
     */
    public final byte[] getValue() {
        return mPropertyValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        final AccessMessage message = (AccessMessage) mMessage;
        dest.writeParcelable(message, flags);
    }
}
