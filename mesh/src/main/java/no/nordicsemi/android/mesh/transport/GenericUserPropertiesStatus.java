package no.nordicsemi.android.mesh.transport;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import no.nordicsemi.android.mesh.ApplicationKey;
import no.nordicsemi.android.mesh.opcodes.ApplicationMessageOpCodes;
import no.nordicsemi.android.mesh.utils.MeshAddress;
import no.nordicsemi.android.mesh.utils.SecureUtils;

/**
 * To be used as a wrapper class when creating a GenericUserPropertiesStatus message.
 */
@SuppressWarnings("unused")
public class GenericUserPropertiesStatus extends GenericStatusMessage implements Parcelable {
    private static final String TAG = GenericUserPropertiesStatus.class.getSimpleName();
    private static final int OP_CODE = ApplicationMessageOpCodes.GENERIC_USER_PROPERTIES_STATUS;

    private short[] mPropertyIDs;

    private static final Parcelable.Creator<GenericUserPropertiesStatus> CREATOR = new Parcelable.Creator<GenericUserPropertiesStatus>() {
        @Override
        public GenericUserPropertiesStatus createFromParcel(Parcel in) {
            final AccessMessage message = in.readParcelable(AccessMessage.class.getClassLoader());
            //noinspection ConstantConditions
            return new GenericUserPropertiesStatus(message);
        }

        @Override
        public GenericUserPropertiesStatus[] newArray(int size) {
            return new GenericUserPropertiesStatus[size];
        }
    };

    /**
     * Constructs the GenericUserPropertiesStatus mMessage.
     *
     * @param message Access Message
     */
    public GenericUserPropertiesStatus(@NonNull final AccessMessage message) {
        super(message);
        this.mParameters = message.getParameters();
        parseStatusParameters();
    }

    @Override
    void parseStatusParameters() {
        Log.v(TAG, "Received generic user properties status from: " + MeshAddress.formatAddress(mMessage.getSrc(), true));
        final ByteBuffer buffer = ByteBuffer.wrap(mParameters).order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);
        mPropertyIDs = new short[buffer.limit()/2];
        if (buffer.limit() > 2) {
            short propID = buffer.getShort();
            mPropertyIDs[buffer.position()/2] = propID;
            Log.v(TAG, "Property ID: " + propID);
        }
    }

    @Override
    int getOpCode() {
        return OP_CODE;
    }

    /**
     * Returns the property IDs of the GenericUserPropertiesStatus
     *
     * @return Property ID array
     */
    public final short[] getPropertyIDs() {
        return mPropertyIDs;
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
