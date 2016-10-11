package net.iquesoft.iquephoto.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import net.iquesoft.iquephoto.DataHolder;
import net.iquesoft.iquephoto.adapters.AdjustAdapter;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.core.EditorImageView;
import net.iquesoft.iquephoto.di.components.IEditorActivityComponent;
import net.iquesoft.iquephoto.model.Adjust;
import net.iquesoft.iquephoto.presenter.AdjustFragmentPresenterImpl;
import net.iquesoft.iquephoto.view.IBrightnessFragmentView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AdjustFragment extends BaseFragment implements IBrightnessFragmentView {

    private boolean isHide;

    private Unbinder unbinder;

    private AdjustAdapter adapter;

    private EditorImageView editorView;

    @BindView(R.id.hideAdjust)
    ImageView hideAdjust;

    @BindView(R.id.adjustRecyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.adjustSeekBar)
    DiscreteSeekBar seekBar;

    @Inject
    AdjustFragmentPresenterImpl presenter;

    public static AdjustFragment newInstance() {
        /*Bundle b = new Bundle();
        b.putString("msg", text);
        b.putString("color", color);
        f.setArguments(b);*/
        return new AdjustFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IEditorActivityComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_adjust, container, false);

       // editorView = DataHolder.getInstance().getEditorView();

        unbinder = ButterKnife.bind(this, v);

        adapter = new AdjustAdapter(Adjust.getAdjustList());
        recyclerView.setLayoutManager(new LinearLayoutManager(null, LinearLayout.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);

        v.setAlpha(0.8f);

        seekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                //editorView.setBrightnessValue(value);
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.hideAdjust)
    void onClickHide(View view) {
        if (!isHide) {
            isHide = true;
            hideAdjust.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_less));
            recyclerView.setVisibility(View.GONE);
        } else {
            isHide = false;
            hideAdjust.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand));
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}