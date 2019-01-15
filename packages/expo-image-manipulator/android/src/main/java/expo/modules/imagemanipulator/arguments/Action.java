package expo.modules.imagemanipulator.arguments;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Action extends HashMap<String, Object> {
  private static final String TAG = "action";

  private static final String KEY_RESIZE = "resize";
  private static final String KEY_ROTATE = "rotate";
  private static final String KEY_FLIP = "flip";
  private static final String KEY_CROP = "crop";

  @Nullable
  private final ActionResize mResize;
  @Nullable
  private final Integer mRotate;
  @Nullable
  private final ActionFlip mFlip;
  @Nullable
  private final ActionCrop mCrop;

  public static Action fromObject(Object options) throws IllegalArgumentException {
    Map optionsMap = Utilities.ensureMap(options, TAG);

    ActionResize resize = optionsMap.containsKey(KEY_RESIZE) ? ActionResize.fromObject(optionsMap.get(KEY_RESIZE)) : null;
    Double rotateDouble = Utilities.getDoubleFromOptions(optionsMap, KEY_ROTATE, TAG + "." + KEY_ROTATE);
    Integer rotate = rotateDouble != null ? rotateDouble.intValue() : null;
    ActionFlip flip = optionsMap.containsKey(KEY_FLIP) ? ActionFlip.fromObject(optionsMap.get(KEY_FLIP)) : null;
    ActionCrop crop = optionsMap.containsKey(KEY_CROP) ? ActionCrop.fromObject(optionsMap.get(KEY_CROP)) : null;

    ArrayList<Object> actions = new ArrayList<>();
    actions.add(resize);
    actions.add(rotate);
    actions.add(flip);
    actions.add(crop);
    int actionsCounter = 0;
    for (Object action : actions) {
      if (action != null) {
        actionsCounter += 1;
      }
    }
    if (actionsCounter != 1) {
      throw new IllegalArgumentException(
          String.format(
              "Single action must contain exactly one transformation from list: ['%s', '%s', '%s', '%s']",
              KEY_RESIZE,
              KEY_ROTATE,
              KEY_FLIP,
              KEY_CROP
          )
      );
    }

    return new Action(resize, rotate, flip, crop);
  }

  private Action(
      @Nullable ActionResize resize,
      @Nullable Integer rotate,
      @Nullable ActionFlip flip,
      @Nullable ActionCrop crop
  ) {
    mResize = resize;
    mRotate = rotate;
    mFlip = flip;
    mCrop = crop;
  }

  @Nullable
  public ActionResize getResize() {
    return mResize;
  }

  @Nullable
  public Integer getRotate() {
    return mRotate;
  }

  @Nullable
  public ActionFlip getFlip() {
    return mFlip;
  }

  @Nullable
  public ActionCrop getCrop() {
    return mCrop;
  }
}

