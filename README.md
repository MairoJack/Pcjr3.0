# Pcjr3.0

##皮城金融Andorid版 3.0
- 主要用了Rxjava + Retrofit2 + MVP
- BaseBean 封装公共响应信息；BaseToolbarActivity 封装Toolbar；BaseSwipeActivity 封装下拉刷新、上拉加载(只支持对 RecyclerView 操作)
## 添加一个模块，比如用户列表UserList
1. 在 bean 下新建User对象
2. 在 api 下的 OauthService(若无需Token，则在 ApiService) 中新增 Observable<BaseBean<List<User>>> getUserList() 方法
3. 在 model 下的 IOAuthModel(若无需Token，则在 IApiModel) 中新增 getUserList() 接口，在 OAuthModel(若无需Token，则在 ApiModel) 中实现该方法
4. 在 data 下的 DataManager 中新增 getUserList() 方法，调用 IOAuthModel 接口中相应方法
2. 在 ui/adapter 下新建UserListAdpater
3. 在 ui/presenter/ivview 下新建 UserView 接口，继承 MvpView
4. 在 ui/presenter/ 下新建 UserPresenter，继承BasePresenter<UserView>，调用 DataManager 中相应方法
5. 在 ui/views/activity 下新建 UserListActivity ，继承 BaseSwipeActivity，实现 UserView
