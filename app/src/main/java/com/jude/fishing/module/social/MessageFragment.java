package com.jude.fishing.module.social;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.jude.beam.bijection.BeamFragment;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.fishing.R;
import com.jude.utils.JUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

/**
 * Created by Mr.Jude on 2015/9/11.
 */
@RequiresPresenter(MessagePresenter.class)
public class MessageFragment extends BeamFragment<MessagePresenter> {

    @InjectView(R.id.around)
    FloatingActionButton around;
    @InjectView(R.id.contacts)
    FloatingActionButton contacts;
    @InjectView(R.id.search)
    FloatingActionButton search;

    ConversationListFragment listFragment;
    @InjectView(R.id.container)
    FrameLayout container;
    @InjectView(R.id.fab_menu)
    FloatingActionMenu fabMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("消息");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.social_fragment_message, container, false);
        ButterKnife.inject(this, root);
        around.setOnClickListener(v -> startActivity(new Intent(getActivity(), AroundActivity.class)));
        search.setOnClickListener(c -> startActivity(new Intent(getActivity(), UserFindActivity.class)));
        contacts.setOnClickListener(v -> startActivity(new Intent(getActivity(), ContactActivity.class)));
        listFragment = ConversationListFragment.getInstance();
        getChildFragmentManager().beginTransaction().add(R.id.container, listFragment, "ChatList").commit();
        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//群组
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "true")//讨论组
                .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//应用公众服务。
                .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//系统
                .build();
        listFragment.setUri(uri);
        fabMenu.setOnTouchListener((v, event) -> {
            JUtils.Log("Touch"+"ACTION_UP:"+event.getAction()+" Open"+fabMenu.isOpened());
            if (event.getAction()==MotionEvent.ACTION_UP&&fabMenu.isOpened()){
                fabMenu.close(true);
                return true;
            }
            return false;
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
