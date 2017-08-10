package com.mapping.commons;

import java.io.Serializable;
import java.util.Iterator;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mapping.enums.MappingStatus;
import com.mapping.parser.input.BaseMappingEntity;

public class EntityInterceptor extends EmptyInterceptor {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(EntityInterceptor.class);

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {

		log.info("save");

		if (!(entity instanceof BaseMappingEntity)) {
			return false;
		}
		setValue(state, propertyNames, "status", MappingStatus.NEW);

		return true;
	}

	@Override
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
		log.info("update");
		if (!(entity instanceof BaseMappingEntity)) {
			return false;
		}

		//if (checkValue(currentState, previousState, propertyNames, "uploadedFile")) {
		setValue(currentState, propertyNames, "status", MappingStatus.UNDERMAPPING);
		return true;
		//}
		//return false;

	}

	private boolean checkValue(Object[] currentState, Object[] previousState, String[] propertyNames, String propertyToCheck) {
		for (int i = 0; i < propertyNames.length; i++) {
			if (propertyNames[i].equalsIgnoreCase(propertyToCheck)) {
				return currentState[i].equals(previousState[i]);
			}
		}
		return true;
	}

	private void setValue(Object[] state, String[] propertyNames, String propertyToSet, Object value) {

		for (int i = 0; i < propertyNames.length; i++) {
			if (propertyNames[i].equalsIgnoreCase(propertyToSet)) {
				state[i] = value;
				break;
			}
		}

	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}
