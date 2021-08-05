package rs.fon.silab.njt.mojezgradespringboot.model;

import java.io.Serializable;

public enum Status implements Serializable{
    PLACEN,
    @Deprecated
    NEPLACEN,
    KREIRAN,
    POSLAT
}
