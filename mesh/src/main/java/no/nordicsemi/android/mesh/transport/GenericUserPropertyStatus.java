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
 * To be used as a wrapper class when creating a GenericUserPropertyStatus message.
 */
@SuppressWarnings("unused")
public class GenericUserPropertyStatus extends GenericStatusMessage implements Parcelable {
    private static final String TAG = GenericOnOffStatus.class.getSimpleName();
    private static final int OP_CODE = ApplicationMessageOpCodes.GENERIC_USER_PROPERTY_STATUS;

    private short mPropertyID;
    private byte mUserAccess;
    private byte[] mPropertyValue;

    private static final Parcelable.Creator<GenericUserPropertyStatus> CREATOR = new Parcelable.Creator<GenericUserPropertyStatus>() {
        @Override
        public GenericUserPropertyStatus createFromParcel(Parcel in) {
            final AccessMessage message = in.readParcelable(AccessMessage.class.getClassLoader());
            //noinspection ConstantConditions
            return new GenericUserPropertyStatus(message);
        }

        @Override
        public GenericUserPropertyStatus[] newArray(int size) {
            return new GenericUserPropertyStatus[size];
        }
    };

    /**
     * Constructs the GenericUserPropertyStatus mMessage.
     *
     * @param message Access Message
     */
    public GenericUserPropertyStatus(@NonNull final AccessMessage message) {
        super(message);
        this.mParameters = message.getParameters();
        parseStatusParameters();
    }

    @Override
    void parseStatusParameters() {
        Log.v(TAG, "Received generic user property status from: " + MeshAddress.formatAddress(mMessage.getSrc(), true));
        final ByteBuffer buffer = ByteBuffer.wrap(mParameters).order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);
        mPropertyID = buffer.getShort();
        mUserAccess = buffer.get();
        mPropertyValue = new byte[buffer.limit() - 3];
        if (buffer.limit() > 2) {
            mPropertyValue[buffer.position()-3] = buffer.get();
        }
    }

    @Override
    int getOpCode() {
        return OP_CODE;
    }

    /**
     * Returns the property ID of the GenericUserPropertyStatus
     *
     * @return property ID
     */
    public final short getPropertyID() {
        return mPropertyID;
    }

    /**
     * Returns the user access of the GenericUserPropertyStatus
     *
     * @return access level
     */
    public final byte getAccessLevel() {
        return mUserAccess;
    }

    /**
     * Returns the value of the GenericUserPropertyStatus
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
