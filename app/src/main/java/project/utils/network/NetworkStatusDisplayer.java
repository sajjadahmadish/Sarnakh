package project.utils.network;

import android.content.res.Resources;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.novoda.merlin.MerlinsBeard;

import ir.sinapp.sarnakh.R;

public class NetworkStatusDisplayer {

    private final Resources resources;
    private final MerlinsBeard merlinsBeard;

    @Nullable
    private MerlinSnackbar snackbar;

    public NetworkStatusDisplayer(Resources resources, MerlinsBeard merlinsBeard) {
        this.resources = resources;
        this.merlinsBeard = merlinsBeard;
    }

    public void displayPositiveMessage(@StringRes int messageResource, View attachTo) {
        snackbar = MerlinSnackbar.withDuration(resources, attachTo, R.integer.snackbar_duration)
                .withText(messageResource)
                .withTheme(new PositiveThemer())
                .show();
    }

    public void displayNegativeMessage(@StringRes int messageResource, View attachTo) {
        snackbar = MerlinSnackbar.withDuration(resources, attachTo, R.integer.snackbar_duration)
                .withText(messageResource)
                .withTheme(new NegativeThemer())
                .show();
    }


    private Themer subtypeThemerFrom(String subtype) {
        if (subtypeAbsent(subtype)) {
            return new NegativeThemer();
        } else {
            return new PositiveThemer();
        }
    }

    private boolean subtypeAbsent(String subtype) {
        return subtype == null || subtype.isEmpty();
    }

    public void reset() {
        if (snackbar == null) {
            return;
        }
        snackbar.dismiss();
        snackbar = null;
    }

}
