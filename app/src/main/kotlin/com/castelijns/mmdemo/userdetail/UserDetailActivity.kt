package com.castelijns.mmdemo.userdetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.castelijns.mmdemo.R
import com.castelijns.mmdemo.models.User
import com.castelijns.mmdemo.users.UsersFragment.Companion.EXTRA_EMAIL_TRANSITION
import com.castelijns.mmdemo.users.UsersFragment.Companion.EXTRA_USER
import com.castelijns.mmdemo.users.UsersFragment.Companion.EXTRA_USERNAME_TRANSITION
import kotlinx.android.synthetic.main.activity_user_detail.*

class UserDetailActivity : AppCompatActivity(), UserDetailContract.View {

    private lateinit var presenter: UserDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        val user = intent.getParcelableExtra<User>(EXTRA_USER)
        presenter = UserDetailPresenter(this, user)

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.title = user.name

        // For shared element transitions.
        supportPostponeEnterTransition()

        tv_username.transitionName = intent.getStringExtra(EXTRA_USERNAME_TRANSITION)
        tv_username.text = user.username

        tv_email.transitionName = intent.getStringExtra(EXTRA_EMAIL_TRANSITION)
        tv_email.text = user.email

        supportStartPostponedEnterTransition()
        // end.

        tv_phone.text = user.phone
        tv_website.text = user.website

        tv_city.text = user.address.city
        tv_address.text = getString(R.string.user_detail_address, user.address.street,
                user.address.suite)
        tv_zipcode.text = user.address.zipcode

        tv_company_name.text = user.company.name
        tv_company_catchphrase.text = user.company.catchPhrase
        tv_company_bs.text = user.company.bs

        ib_directions.setOnClickListener { presenter.onDirectionsClicked() }
        presenter.start()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    override fun showNavigationTo(lat: String, lon: String) {
        val intent = Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.google.com/maps/dir/?api=1&destination=$lat,$lon"))
        startActivity(intent)
    }

}
