package fr.improve.struts.taglib.layout.policy;

import fr.improve.struts.taglib.layout.field.AbstractModeFieldTag;

public interface Policy {
	public static final short MODE_DISPLAY = 100;
	public static final short MODE_NODISPLAY = AbstractModeFieldTag.MODE_NODISPLAY;
	public static final short MODE_HIDDEN = AbstractModeFieldTag.MODE_HIDDEN;
	public static final short MODE_INSPECT = AbstractModeFieldTag.MODE_INSPECT;
	public static final short MODE_INSPECT_ONLY = AbstractModeFieldTag.MODE_INSPECT_ONLY;
	public static final short MODE_INSPECT_PRESENT = AbstractModeFieldTag.MODE_INSPECT_PRESENT;
	public static final short MODE_EDIT = AbstractModeFieldTag.MODE_EDIT;
	public static final short MODE_READONLY = AbstractModeFieldTag.MODE_READONLY;
	public static final short MODE_DISABLED = AbstractModeFieldTag.MODE_DISABLED;
	public static final short MODE_CELL = AbstractModeFieldTag.MODE_CELL;
}
