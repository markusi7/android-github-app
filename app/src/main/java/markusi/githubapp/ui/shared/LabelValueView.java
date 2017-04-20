package markusi.githubapp.ui.shared;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import markusi.githubapp.R;
import markusi.githubapp.utils.ViewUtils;

/**
 * Simple {@link android.view.View} which can display a value and a label, supplied via XML or programmatically.
 */
public class LabelValueView extends LinearLayout {

    @BindView(R.id.value)
    TextView value;

    @BindView(R.id.label)
    TextView label;

    public LabelValueView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public LabelValueView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public LabelValueView(Context context, @Nullable AttributeSet attrs,
            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    // because we are using <merge> tag in layout, we need to set root view data programmatically
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.view_label_value, this, true);
        ButterKnife.bind(this);
        setOrientation(VERTICAL);
        if (!isInEditMode()) {
            //causes render errors in layout preview
//            getLayoutParams().height = ViewUtils.getDimension(getContext(), android.R.attr.listPreferredItemHeight);
            setBackground(ContextCompat.getDrawable(getContext(), ViewUtils.getResourceId(context, R.attr.selectableItemBackground)));
        }
        int paddingVertical = getResources().getDimensionPixelOffset(R.dimen.spacing_1x);
        int paddingHorizontal = getResources().getDimensionPixelOffset(R.dimen.spacing_2x);
        setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical);

        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LabelValueView, defStyleAttr, 0);
        if (typedArray != null) {
            setLabel(typedArray.getText(R.styleable.LabelValueView_detail_label));
            setValue(typedArray.getText(R.styleable.LabelValueView_detail_value));
            typedArray.recycle();
        }
    }

    public void setValue(CharSequence text) {
        value.setText(text);
    }

    public void setLabel(CharSequence text) {
        label.setText(text);
    }
}
