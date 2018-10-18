package com.castelijns.mmdemo.users

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.castelijns.mmdemo.R
import com.castelijns.mmdemo.app.BaseListFragment
import com.castelijns.mmdemo.models.User
import com.castelijns.mmdemo.userdetail.UserDetailActivity

import java.util.ArrayList
import java.util.Locale
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_users.*

class UsersFragment : BaseListFragment(), UsersContract.View {


    private var presenter: UsersPresenter? = null

    private var adapter: UsersAdapter? = null
    private var users: MutableList<User>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = UsersPresenter(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        users = ArrayList()
        adapter = UsersAdapter(users!!)
        adapter!!.setItemClickListener(object : ItemClickListener {
            override fun onClick(user: User, tvUsername: TextView, tvEmail: TextView) {
                presenter!!.onUserClicked(
                        user, tvUsername, tvEmail)
            }
        })

        rv_users.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = adapter
        }

        presenter!!.start()
    }

    override fun onDestroyView() {
        presenter!!.stop()
        super.onDestroyView()
    }

    override fun showUsers(users: List<User>) {
        this.users!!.addAll(users)
        adapter!!.notifyDataSetChanged()
    }

    override fun showUserCount(count: Int) {
        setHeaderText(String.format(Locale.getDefault(), "%d %s", count, getString(R.string.header_users)))
    }

    override fun showUserDetail(user: User, tvUsername: TextView, tvEmail: TextView) {
        val transitionUsername = ViewCompat.getTransitionName(tvUsername)
        val transitionEmail = ViewCompat.getTransitionName(tvEmail)

        val intent = Intent(context, UserDetailActivity::class.java)
        intent.putExtra(EXTRA_USER, user)
        intent.putExtra(EXTRA_USERNAME_TRANSITION, transitionUsername)
        intent.putExtra(EXTRA_EMAIL_TRANSITION, transitionEmail)

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity!!,
                Pair.create<View, String>(tvUsername, transitionUsername),
                Pair.create<View, String>(tvEmail, transitionEmail)
        )

        startActivity(intent, options.toBundle())
    }

    companion object {

        val EXTRA_USER = "extra_user"
        val EXTRA_USERNAME_TRANSITION = "extra_username_transition"
        val EXTRA_EMAIL_TRANSITION = "extra_email_transition"
    }
}
