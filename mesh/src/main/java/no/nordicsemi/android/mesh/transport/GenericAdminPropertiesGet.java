package no.nordicsemi.android.mesh.transport;

import androidx.annotation.NonNull;

import no.nordicsemi.android.mesh.ApplicationKey;
import no.nordicsemi.android.mesh.opcodes.ApplicationMessageOpCodes;
import no.nordicsemi.android.mesh.utils.SecureUtils;

/**
 * To be used as a wrapper class when creating a GenericAdminPropertiesGet message.
 */
@SuppressWarnings("unused")
public class GenericAdminPropertiesGet extends GenericMessage {
    private static final String TAG = GenericAdminPropertiesGet.class.getSimpleName();
    private static final int OP_CODE = ApplicationMessageOpCodes.GENERIC_ADMIN_PROPERTIES_GET;

    /**
     * Constructs GenericAdminPropertiesGet message.
     *
     * @param appKey application key for this message
     * @throws IllegalArgumentException if any illegal arguments are passed
     */
    public GenericAdminPropertiesGet(@NonNull final ApplicationKey appKey) throws IllegalArgumentException {
        super(appKey);
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