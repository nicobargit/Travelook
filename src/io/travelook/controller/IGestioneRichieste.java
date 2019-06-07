package io.travelook.controller;

import io.travelook.model.RichiestaDiPartecipazione;

public interface IGestioneRichieste {
	public abstract boolean nuovaRichiesta(RichiestaDiPartecipazione richiesta);
    public abstract boolean rispondiRichiesta(RichiestaDiPartecipazione richiesta);
}
