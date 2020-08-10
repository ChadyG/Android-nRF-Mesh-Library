package no.nordicsemi.android.mesh.transport;

import androidx.annotation.NonNull;

import no.nordicsemi.android.mesh.ApplicationKey;
import no.nordicsemi.android.mesh.opcodes.ApplicationMessageOpCodes;
import no.nordicsemi.android.mesh.utils.SecureUtils;

/**
 * To be used as a wrapper class when creating a GenericUserPropertyGet message.
 */
@SuppressWarnings("unused")
public class GenericUserPropertyGet extends GenericMessage {

    private static final String TAG = GenericOnOffGet.class.getSimpleName();
    private static final int OP_CODE = ApplicationMessageOpCodes.GENERIC_USER_PROPERTY_GET;

    /**
     * Constructs GenericUserPropertyGet message.
     *
     * @param appKey application key for this message
     * @throws IllegalArgumentException if any illegal arguments are passed
     */
    public GenericUserPropertyGet(@NonNull final ApplicationKey appKey) throws IllegalArgumentException {
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
