package no.nordicsemi.android.mesh.transport;

import androidx.annotation.NonNull;

import no.nordicsemi.android.mesh.ApplicationKey;
import no.nordicsemi.android.mesh.opcodes.ApplicationMessageOpCodes;
import no.nordicsemi.android.mesh.utils.SecureUtils;

/**
 * To be used as a wrapper class when creating a GenericAdminPropertyGet message.
 */
@SuppressWarnings("unused")
public class GenericAdminPropertyGet extends GenericMessage {

    private static final String TAG = GenericAdminPropertyGet.class.getSimpleName();
    private static final int OP_CODE = ApplicationMessageOpCodes.GENERIC_ADMIN_PROPERTY_GET;
    private static final int GENERIC_ADMIN_PROPERTY_GET_PARAMS_LENGTH = 2;

    private short mPropertyID;

    /**
     * Constructs GenericAdminPropertyGet message.
     *
     * @param appKey application key for this message
     * @throws IllegalArgumentException if any illegal arguments are passed
     */
    public GenericAdminPropertyGet(@NonNull final ApplicationKey appKey,
                                   final short propertyId) throws IllegalArgumentException {
        super(appKey);
        mPropertyID = propertyId;
        assembleMessageParameters();
    }

    @Override
    public int getOpCode() {
        return OP_CODE;
    }

    @Override
    void assembleMessageParameters() {
        mAid = SecureUtils.calculateK4(mAppKey.getKey());
    }
}
