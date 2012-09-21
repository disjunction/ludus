package com.pluseq.mire.storage;
import java.util.Hashtable;
import com.pluseq.mire.core.Location;
import com.pluseq.vivid.*;

public class LocationHolder extends VividHolder implements HashtableHolderInterface {	
	public LocationHolder(Location l) {
		set(l);
	}

	@Override
	public Hashtable<String, Object> toHashtable() {
		Hashtable<String, Object> ht = new Hashtable<String, Object>();
		Location l = (Location)get();
		ht.put("id", l.id);
		ht.put("title", l.title);
		return ht;
	}

	@Override
	public String getStringId() {
		if (0 == ((Location)get()).id) {
			return null;
		}
		return Integer.toString(((Location)get()).id);
	}

	@Override
	public boolean save() {
		return MireStorage._().getLocationTable().save(this);
	}
}
