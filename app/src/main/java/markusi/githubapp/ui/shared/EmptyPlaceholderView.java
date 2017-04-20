package markusi.githubapp.ui.shared;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import markusi.githubapp.R;

/**
 * Simple {@link android.view.View} which usually serves as a indicator that a list is empty or has no results.
 */
public class EmptyPlaceholderView extends RelativeLayout {

    @BindView(R.id.textView)
    TextView textView;

    private CharSequence initialText;

    private CharSequence noSearchResultsText;

    public EmptyPlaceholderView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public EmptyPlaceholderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public EmptyPlaceholderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    // same goes for root view attributes as in DetailItemView
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.empty_placeholder, this, true);
        ButterKnife.bind(this);

        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EmptyPlaceholderView, defStyleAttr, 0);
        if (typedArray != null) {

            initialText = typedArray.getText(R.styleable.EmptyPlaceholderView_initialText);
            noSearchResultsText = typedArray.getText(R.styleable.EmptyPlaceholderView_noSearchResultsText);

            textView.setText(initialText);

            Drawable drawable = typedArray.getDrawable(R.styleable.EmptyPlaceholderView_android_src);
            setDrawableTop(drawable);

            typedArray.recycle();
        }
    }

    public void setData(@StringRes int initialTextResourceId, @StringRes int noSearchResultsTextResourceId,
            @DrawableRes int drawableResourceId) {
        initialText = getContext().getString(initialTextResourceId);
        noSearchResultsText = getContext().getString(noSearchResultsTextResourceId);
        setDrawableTop(ContextCompat.getDrawable(getContext(), drawableResourceId));
    }

    private void setDrawableTop(Drawable drawableTop) {
        textView.setCompoundDrawablesWithIntrinsicBounds(null, drawableTop, null, null);
    }

    public void setState(State state) {
        textView.setText(state.equals(State.INITIAL) ? initialText : noSearchResultsText);
    }

    public enum State {
        INITIAL,
        NO_RESULTS
    }
}
