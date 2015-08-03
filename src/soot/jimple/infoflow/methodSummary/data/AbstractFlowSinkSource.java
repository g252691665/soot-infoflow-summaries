package soot.jimple.infoflow.methodSummary.data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import soot.jimple.infoflow.Infoflow;
import soot.jimple.infoflow.methodSummary.xml.XMLConstants;

/**
 * Data class which stores the data associated to a Sink or a Source of a method
 * flow.
 */
public abstract class AbstractFlowSinkSource {
	protected final SourceSinkType type;
	protected final int parameterIdx;
	protected final String baseType;
	protected final String[] accessPath;
	protected final String[] accessPathTypes;
	protected final GapDefinition gap;

	public AbstractFlowSinkSource(SourceSinkType type, int parameterIdx,
			String baseType) {
		this(type, parameterIdx, baseType, null, null);
	}
	
	public AbstractFlowSinkSource(SourceSinkType type, int parameterIdx,
			String baseType, String[] accessPath, String[] accessPathTypes) {
		this(type, parameterIdx, baseType, accessPath, accessPathTypes, null);
	}
		
	public AbstractFlowSinkSource(SourceSinkType type, int parameterIdx,
			String baseType, String[] accessPath, String[] accessPathTypes,
			GapDefinition gap) {
		this.type = type;
		this.parameterIdx = parameterIdx;
		this.baseType = baseType;
		this.accessPath = limitAccessPath(accessPath);
		this.accessPathTypes = limitAccessPath(accessPathTypes);
		this.gap = gap;
	}
	
	/**
	 * Truncates the access path at the configured maximum length
	 * @param in The incoming access path to truncate
	 * @return The truncated access path
	 */
	private static String[] limitAccessPath(String[] in) {
		if (in == null || in.length == 0)
			return null;
		
		if (in.length > Infoflow.getAccessPathLength()) {
			String[] out = new String[Infoflow.getAccessPathLength()];
			System.arraycopy(in, 0, out, 0, Infoflow.getAccessPathLength());
			return out;
		}
		return in;
	}

	/**
	 * Checks whether the current source or sink is coarser than the given one,
	 * i.e., if all elements referenced by the given source or sink are also
	 * referenced by this one
	 * @param src The source or sink with which to compare the current one
	 * @return True if the current source or sink is coarser than the given one,
	 * otherwise false
	 */
	public boolean isCoarserThan(AbstractFlowSinkSource other) {
		if (this.equals(other))
			return true;
		
		if (this.type != other.type
				|| this.parameterIdx != other.parameterIdx
				|| !safeCompare(this.baseType, other.baseType)
				|| !safeCompare(this.gap, other.gap))
			return false;
		if (this.accessPath != null && other.accessPath != null) {
			if (this.accessPath.length > other.accessPath.length)
				return false;
			for (int i = 0; i < this.accessPath.length; i++)
				if (!this.accessPath[i].equals(other.accessPath[i]))
					return false;
		}
		return true;
	}
	
	public SourceSinkType type(){
		return type;
	}

	public boolean isParameter() {
		return type().equals(SourceSinkType.Parameter);
	}
	
	public boolean isThis()
	{
		return type().equals(SourceSinkType.Field) && !hasAccessPath();
	}
	
	public int getParameterIndex() {
		return parameterIdx;
	}
	
	public String getBaseType() {
		return baseType;
	}
	
	/**
	 * Gets whether this taint is on a *base* field. Note that this does not
	 * include fields starting on parameters or return values.
	 * @return True if this taint references a base field, false otherwise
	 */
	public boolean isField() {
		return type().equals(SourceSinkType.Field);
	}
	
	/**
	 * Gets the number of fields in this access path. If this taint has no
	 * access path, zero is returned.
	 * @return The number of fields in this access path
	 */
	public int getFieldCount() {
		return accessPath == null ? 0 : accessPath.length;
	}

	public String[] getAccessPath() {
		return accessPath;
	}
	
	public String[] getAccessPathTypes() {
		return accessPathTypes;
	}

	public boolean isReturn() {
		return type().equals(SourceSinkType.Return);
	}

	public boolean isGapBaseObject() {
		return type().equals(SourceSinkType.GapBaseObject);
	}

	public boolean hasAccessPath() {
		return accessPath != null && accessPath.length > 0;
	}

	public int getAccessPathLength() {
		if (hasAccessPath())
			return accessPath.length;
		return 0;
	}

	public SourceSinkType getType() {
		return this.type;
	}
	
	public GapDefinition getGap() {
		return this.gap;
	}
	
	public String getLastFieldType() {
		if (accessPathTypes == null || accessPathTypes.length == 0)
			return baseType;
		return accessPathTypes[accessPathTypes.length - 1];
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accessPath == null) ? 0 : Arrays.hashCode(accessPath));
		result = prime * result + parameterIdx;
		result = prime * result + (baseType == null ? 0 : baseType.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((gap == null) ? 0 : gap.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractFlowSinkSource other = (AbstractFlowSinkSource) obj;
		if (accessPath == null) {
			if (other.accessPath != null)
				return false;
		} else if (!Arrays.equals(accessPath, other.accessPath))
			return false;
		if (parameterIdx != other.parameterIdx)
			return false;
		if (baseType == null) {
			if (other.baseType != null)
				return false;
		}
		else if (!baseType.equals(other.baseType))
			return false;
		if (type != other.type)
			return false;
		if (gap == null) {
			if (other.gap != null)
				return false;
		}
		else if (!gap.equals(other.gap))
			return false;
		return true;
	}
	
	protected boolean safeCompare(Object o1, Object o2) {
		if (o1 == null)
			return o2 == null;
		if (o2 == null)
			return o1 == null;
		return o1.equals(o2);
	}
	
	public Map<String, String> xmlAttributes() {
		Map<String, String> res = new HashMap<String, String>();
		if (isParameter()) {
			res.put(XMLConstants.ATTRIBUTE_FLOWTYPE, XMLConstants.VALUE_PARAMETER);
			res.put(XMLConstants.ATTRIBUTE_PARAMTER_INDEX, getParameterIndex() + "");
		}
		else if (isField())
			res.put(XMLConstants.ATTRIBUTE_FLOWTYPE, XMLConstants.VALUE_FIELD);
		else if (isReturn())
			res.put(XMLConstants.ATTRIBUTE_FLOWTYPE, XMLConstants.VALUE_RETURN);
		else
			throw new RuntimeException("Invalid source type");
		
		if (baseType != null)
			res.put(XMLConstants.ATTRIBUTE_BASETYPE, baseType);
		if (hasAccessPath())
			res.put(XMLConstants.ATTRIBUTE_ACCESSPATH, getAccessPath().toString());
		if (gap != null)
			res.put(XMLConstants.ATTRIBUTE_GAP, getGap().getID() + "");
		
		return res;
	}
	
}
