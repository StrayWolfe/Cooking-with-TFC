package straywolfe.cookingwithtfc.api.managers;

import java.util.ArrayList;
import java.util.List;

public class CropManager 
{
	public List<CWTFCCropIndex> crops;
	public List<CWTFCCropIndex> wildcrops;

	protected static final CropManager INSTANCE = new CropManager();

	public static final CropManager getInstance()
	{
		return INSTANCE;
	}

	public CropManager()
	{
		crops = new ArrayList<CWTFCCropIndex>();
		wildcrops = new ArrayList<CWTFCCropIndex>();
	}

	public void addCrop(CWTFCCropIndex index)
	{
		crops.add(index);
	}
	
	public void addWildCrop(CWTFCCropIndex index)
	{
		crops.add(index);
		wildcrops.add(index);
	}

	public CWTFCCropIndex getCropFromId(int n)
	{
		for(CWTFCCropIndex pi : crops)
			if(pi.cropId == n)
				return pi;
		return null;
	}
}
