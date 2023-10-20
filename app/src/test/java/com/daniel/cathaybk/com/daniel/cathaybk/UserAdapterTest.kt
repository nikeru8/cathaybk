package com.daniel.cathaybk.com.daniel.cathaybk

import android.app.Activity
import com.daniel.cathaybk.adapter.UserAdapter
import com.daniel.cathaybk.model.UserItem
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UserAdapterTest {

    private lateinit var userAdapter: UserAdapter

    @Before
    fun setUp() {
        val context = mock(Activity::class.java)
        userAdapter = UserAdapter(context)
    }

    @Test
    fun `when updateUser is called, the size should be updated correctly`() {
        val userList: MutableList<UserItem> = mutableListOf(
            UserItem(avatarUrl = "url1", id = 1),
            UserItem(avatarUrl = "url2", id = 2)
        )

        userAdapter.updateUser(userList)

        assertEquals(3, userAdapter.itemCount) // 2 users + 1 footer
    }
}