package no.nordicsemi.android.mesh.transport;

import android.util.Log;

import androidx.annotation.NonNull;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import no.nordicsemi.android.mesh.ApplicationKey;
import no.nordicsemi.android.mesh.opcodes.ApplicationMessageOpCodes;
import no.nordicsemi.android.mesh.utils.SecureUtils;

/**
 * To be used as a wrapper class when creating a GenericAdminPropertySetUnacknowledged message.
 */
@SuppressWarnings("unused")
public class GenericAdminPropertySetUnacknowledged extends GenericMessage {

    private static final String TAG = GenericOnOffSet.class.getSimpleName();
    private static final int OP_CODE = ApplicationMessageOpCodes.GENERIC_ADMIN_PROPERTY_SET_UNACKNOWLEDGED;
    private static final int GENERIC_ADMIN_PROPERTY_SET_PARAMS_LENGTH = 3;

    private short mPropertyID;
    private byte mUserAccess;
    private byte[] mPropertyValue;

    private final int tId;

    /**
     * Constructs GenericAdminPropertySetUnacknowledged message.
     *
     * @param appKey {@link ApplicationKey} key for this message
     * @param propertyID  property ID of the GenericAdminPropertySet
     * @param value  value of the GenericAdminPropertySet
     * @param tId    Transaction id
     * @throws IllegalArgumentException if any illegal arguments are passed
     */
    public GenericAdminPropertySetUnacknowledged(@NonNull final ApplicationKey appKey,
                                   final short propertyID,
                                   final byte[] value,
                                   final int tId) throws IllegalArgumentException {
        this(appKey, propertyID, (byte) 0x03, value, tId);
    }

    /**
     * Constructs GenericAdminPropertySetUnacknowledged message.
     *
     * @param appKey               {@link ApplicationKey} key for this message
     * @param propertyID  property ID of the GenericAdminPropertySet
     * @param accessLevel  access level of the GenericAdminPropertySet
     * @param value  value of the GenericAdminPropertySet
     * @param tId                  Transaction id
     * @throws IllegalArgumentException if any illegal arguments are passed
     */
    public GenericAdminPropertySetUnacknowledged(@NonNull final ApplicationKey appKey,
                                   final short propertyID,
                                   final byte accessLevel,
                                   final byte[] value,
                                   final int tId) {
        super(appKey);
        this.mPropertyID = propertyID;
        this.mUserAccess = accessLevel;
        this.mPropertyValue = value;
        this.tId = tId;
        assembleMessageParameters();
    }

    @Override
    public int getOpCode() {
        return OP_CODE;
    }

    @Override
    void assembleMessageParameters() {
        mAid = SecureUtils.calculateK4(mAppKey.getKey());
        final ByteBuffer paramsBuffer;
        Log.v(TAG, "Property: " + mPropertyID);
        paramsBuffer = ByteBuffer.allocate(GENERIC_ADMIN_PROPERTY_SET_PARAMS_LENGTH + mPropertyValue.length).order(ByteOrder.LITTLE_ENDIAN);
        paramsBuffer.putShort(mPropertyID);
        paramsBuffer.put((byte) mUserAccess);
        paramsBuffer.put(mPropertyValue);
        mParameters = paramsBuffer.array();

    }
}
