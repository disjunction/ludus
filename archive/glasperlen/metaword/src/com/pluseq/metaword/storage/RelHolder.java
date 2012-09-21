package com.pluseq.metaword.storage;
import java.util.Hashtable;
import com.pluseq.metaword.core.*;
import com.pluseq.vivid.*;

public class RelHolder extends VividAutoSavedHolder implements HashtableHolderInterface {
	
	public RelHolder(Rel Rel) {
		set(Rel);
	}

	public RelHolder() {
	}

	@Override
	public Rel get() {
		return (Rel) super.get();
	}

	@Override
	public boolean save() {
		return MetawordStorage._().getRelTable().save(this);
	}

	@Override
	public Hashtable<String, Object> toHashtable() {
		Rel Rel = get();
		Hashtable<String, Object> h = new Hashtable<String, Object>();
		h.put("subjSign", Rel.subjSign);
		h.put("objSign", Rel.objSign);
		h.put("typeSign", Rel.typeSign);
		h.put("value", Integer.valueOf(Rel.value));
		return h;
	}
	
	@Override
	public String getStringId() {
		return null;
	}
}
