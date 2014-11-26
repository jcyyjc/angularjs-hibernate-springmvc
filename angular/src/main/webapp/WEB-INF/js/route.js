var app = angular.module('memo',['ui.router', 'memoService', 'ngCookies']);
app.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, urlRouterProvider) {
	urlRouterProvider.when('', '/login')
	.when('/main', '/main/content/0/2')
	.when('/user', '/user/detail')
	$stateProvider
	.state('login', {
		url: "/login",
		views: {
			'naivView': {
				templateUrl: 'html/signTitle.html',
				controller: 'loginController'
			},
			'mainView': {
				templateUrl: 'html/login.html',
				controller: 'loginController'
			}
		}
	})
	.state('regisiter', {
		url: '/regisiter',
		views: {
			'naivView': {
				templateUrl: 'html/regisiterTitle.html',
				controller: 'loginController'
			},
			'mainView': {
				templateUrl: 'html/regisiter.html',
				controller: 'regisiterController'
			}
		}
	})
	.state('main', {
		url: '/main',
		views: {
			'naivView': {
				templateUrl: 'html/title.html',
				controller: 'titleController'
			},
			'mainView': {
				templateUrl: 'html/main.html',
				controller: 'contentController'
			}
		}
	})
	.state('main.content', {
		url: '/content',
		views: {
			'memoView': {
				templateUrl: 'html/content.html',
				controller: 'mainController'
			}
		}
	})
	.state('main.content.list', {
		url: '/:condition/:status',
		views: {
			'listView': {
				templateUrl: 'html/memoList.html',
				controller: 'listController'
			}
		}
	})	
	.state('user', {
		url: '/user',
		views: {
			'naivView': {
				templateUrl: 'html/title.html',
				controller: 'titleController'
			},
			'mainView': {
				templateUrl: 'html/userInfo.html',
				controller: 'userController'
			}
		}
	})
	.state('user.detail', {
		url: '/detail',
		views: {
			'userDetailView': {
				templateUrl: 'html/userDetail.html',
				controller: 'userDetailController'
			}
		}
	})
	.state('user.modify', {
		url: '/modify',
		views: {
			'userDetailView': {
				templateUrl: 'html/updateUser.html',
				controller: 'userDetailController'
			}
		}
	})
	.state('user.password', {
		url: '/password',
		views: {
			'userDetailView':{
				templateUrl: 'html/changePassword.html',
				controller: 'passwordController'
			} 
		}
	})
}]);


