package com.daniel.cathaybk.com.daniel.cathaybk

import com.daniel.cathaybk.api.GitHubApi
import com.daniel.cathaybk.model.User
import com.daniel.cathaybk.model.UserItem
import com.daniel.cathaybk.presenter.UserContract
import com.daniel.cathaybk.presenter.UserPresenter
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserPresenterTest {

    @Mock
    private lateinit var view: UserContract.View

    @Mock
    private lateinit var gitHubApi: GitHubApi

    private lateinit var presenter: UserPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = UserPresenter(view, gitHubApi)
    }

    @Test
    fun testFetchUsersSuccess() {

        // 模擬 API 成功回應，使用包含 UserItem 物件的列表
        val userList = createMockUserList()
        //創建了一個模擬的 Retrofit Response 對象，表示成功的 API 回應，並將 userList 放入回應中
        val mockResponse = Response.success(userList)
        //創建了一個模擬的 Retrofit Call 對象，並設定它的返回類型為 User。這是因為 gitHubApi.getUsers 函數返回的是一個 Call<User>
        val mockCall = Mockito.mock(Call::class.java) as Call<User> // 正確匹配返回類型
        //使用 Mockito 模擬了 gitHubApi 的 getUsers 函數，並告訴它在被調用時返回上面創建的模擬 mockCall。
        Mockito.`when`(gitHubApi.getUsers(Mockito.anyInt(), Mockito.anyInt())).thenReturn(mockCall)
        //擬了 mockCall 的 enqueue 函數，它使用 thenAnswer 函數來處理當 enqueue 被呼叫時的情況。
        //在這裡，它調用傳遞給 enqueue 的回調函數，模擬 API 請求成功的情況，並呼叫回調的 onResponse 函數，傳遞模擬的回應 mockResponse
        Mockito.`when`(mockCall.enqueue(Mockito.any())).thenAnswer {
            val callback = it.getArgument<Callback<User>>(0)
            callback.onResponse(mockCall, mockResponse)
        }

        //實際執行 presenter.fetchUsers() 並驗證結果
        presenter.fetchUsers()

        //驗證 view 的 showUsers 函數是否被呼叫，並且傳遞了模擬的 userList
        Mockito.verify(view).showUsers(userList)
        //驗證 view 的 showError 函數是否永遠不會被呼叫，因為我們在測試中模擬了成功的 API 回應，所以不應該出現錯誤。
        Mockito.verify(view, Mockito.never()).showError(Mockito.anyString())

    }


    @Test //測試當 API 呼叫失敗時，presenter.fetchUsers() 正確處理錯誤情況並調用 view.showError 函數
    fun testFetchUsersFailure() {

        //使用 Mockito 創建一個模擬的 Retrofit Call 對象，這個對象的返回類型應該是 User。
        // 這是因為 gitHubApi.getUsers 函數返回的是一個 Call<User>
        val mockCall = Mockito.mock(Call::class.java) as Call<User>
        //模擬了 gitHubApi.getUsers 函數的行為。它告訴 Mockito 當這個函數被呼叫時，應該返回我們創建的模擬 mockCall。
        Mockito.`when`(gitHubApi.getUsers(Mockito.anyInt(), Mockito.anyInt())).thenReturn(mockCall)

        //創建了一個模擬的運行時異常，用來模擬 API 呼叫失敗的情況。
        val exception = RuntimeException("Simulated API error") // 模擬一個異常

        //模擬了 mockCall 的 enqueue 函數的行為。
        //當 enqueue 被呼叫時，我們使用 thenAnswer 函數來處理這個呼叫。
        //在這個處理函數中，我們獲取回調函數（Callback<User>）並呼叫 onFailure，傳遞模擬的 mockCall 和我們創建的 exception
        //模擬 API 呼叫失敗的情況。
        Mockito.`when`(mockCall.enqueue(Mockito.any())).thenAnswer {
            val callback = it.getArgument<Callback<User>>(0)
            callback.onFailure(mockCall, exception)
        }

        presenter.fetchUsers()

        // 檢查是否調用了 view 的 showError 函數
        Mockito.verify(view).showError("Simulated API error")

    }

    @Test
    fun testFetchUsersUnsuccessfulResponse() {
        // 创建一个模拟的 Retrofit Call 对象，返回类型为 User
        val mockCall = Mockito.mock(Call::class.java) as Call<User>
        // 模拟 gitHubApi.getUsers 函数的行为，返回我们创建的模拟 mockCall
        Mockito.`when`(gitHubApi.getUsers(Mockito.anyInt(), Mockito.anyInt())).thenReturn(mockCall)

        // 创建一个模拟的 ResponseBody
        val errorBody = Mockito.mock(ResponseBody::class.java)
        // 创建一个模拟的 Response，设置 isSuccessful 为 false，同时传递模拟的 errorBody
        val mockResponse = Response.error<User>(404, errorBody)

        // 模拟 mockCall 的 enqueue 函数的行为，当 enqueue 被调用时，使用 thenAnswer 函数处理这个调用
        // 在這個處理函數中，我们获取回调函数（Callback<User>）并调用 onResponse，传递模擬的 mockResponse
        Mockito.`when`(mockCall.enqueue(Mockito.any())).thenAnswer {
            val callback = it.getArgument<Callback<User>>(0)
            callback.onResponse(mockCall, mockResponse)
        }

        presenter.fetchUsers()

        // 检查是否调用了 view 的 showError 函数，并传递了模拟的错误信息
        Mockito.verify(view).showError("Unknown error response.isUnsuccessful")
    }

    @Test
    fun testFetchUsersError() {
        // 模拟一个失败的 API 响应
        val errorResponse = Response.error<User>(404, Mockito.mock(ResponseBody::class.java))

        // 创建一个模拟的 Retrofit Call 对象，返回类型为 User
        val mockCall = Mockito.mock(Call::class.java) as Call<User>
        // 模拟 gitHubApi.getUsers 函数的行为，返回我们创建的模拟 mockCall
        Mockito.`when`(gitHubApi.getUsers(Mockito.anyInt(), Mockito.anyInt())).thenReturn(mockCall)

        // 模拟 mockCall 的 enqueue 函数的行为，当 enqueue 被调用时，使用 thenAnswer 函数处理这个调用
        // 在这个处理函数中，我们获取回调函数（Callback<User>）并调用 onResponse，传递模拟的 errorResponse，模拟 API 调用失败
        Mockito.`when`(mockCall.enqueue(Mockito.any())).thenAnswer {
            val callback = it.getArgument<Callback<User>>(0)
            callback.onResponse(mockCall, errorResponse)
        }

        // 模拟 view.showError 函数的调用，使用 "Unknown error response.isUnsuccessful" 作为参数
        val errorMessage = "Unknown error response.isUnsuccessful"
        Mockito.doNothing().`when`(view).showError(errorMessage)

        presenter.fetchUsers()

        // 验证 view.showError 函数是否被调用，并且传递了正确的错误信息
        Mockito.verify(view).showError(errorMessage)
    }

    private fun createMockUserList(): User {
        val user1 = UserItem(id = 1, login = "User1", avatarUrl = "Avatar1", siteAdmin = false)
        val user2 = UserItem(id = 2, login = "User2", avatarUrl = "Avatar2", siteAdmin = true)
        val user3 = UserItem(id = 3, login = "User3", avatarUrl = "Avatar3", siteAdmin = false)
        val userList = User()
        userList.add(user1)
        userList.add(user2)
        userList.add(user3)
        return userList
    }
}
