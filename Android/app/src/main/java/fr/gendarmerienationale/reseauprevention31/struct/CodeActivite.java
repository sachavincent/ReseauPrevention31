package fr.gendarmerienationale.reseauprevention31.struct;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CodeActivite {

    private int    code;
    private String activite;

    public int getCode() {
        return this.code;
    }

    public String getActivite() {
        return this.activite;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }

    @NonNull
    @Override
    public String toString() {
        return code + " - " + activite;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (o instanceof CodeActivite)
            return code == ((CodeActivite) o).code;

        return false;
    }
}
