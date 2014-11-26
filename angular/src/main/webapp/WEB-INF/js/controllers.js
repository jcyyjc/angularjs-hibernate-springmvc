app.controller('loginController', ['$scope', '$state', '$rootScope', 'authService', '$http', '$cookieStore',
    function($scope, $state, $rootScope, authService, $http, $cookieStore) {
//		$rootScope.$on('$stateChangeStart', function(event, to, from, toState,fromParams) {
//			console.log($cookieStore.get('isLoged'))
//			console.log($cookieStore.get('userName'))
//		});
		//如果cookie中发现用户的cookie时，直接跳到主页面
		//缺点：用户的信息的安全性不强，暂时还不会在网页关闭后cookie就删除的操作
		if ($cookieStore.get('isLoged')) {
			$state.go('main');
		}
		//用户登录，以及将基本信息保存到cookie中
		$scope.login = function() {
			authService.getUserInfo($scope.log).then(function(data) {
				if (data.check) {
					$cookieStore.put('userName', $scope.log.userName);
					$cookieStore.put('isLoged', true);
					$state.go('main', 'login', true);
				}
			});
		};
		$scope.reset = function() {
			$scope.log = '';
		}
		//跳转到注册页面
		$scope.regisiter = function() {
			$state.go('regisiter');
		}
}]);
//用户注册
app.controller('regisiterController', ['$scope', 'memoRequest',
    function($scope, memoRequest) {
	$scope.regisiter = function() {
		if ($scope.user.password === $scope.user.password2 ) {
			memoRequest.regisiter.save($scope.user, function(data) {
				if (data.check) {
					$scope.user = {};
					$scope.user.sex = 1;
				}
			});
		}
	};
}]);
//主页面的浮动导航栏
app.controller('titleController',['$scope', 'authService', '$cookieStore', '$state', '$rootScope',
    function($scope, authService, $cookieStore, $state, $rootScope) {
		$scope.userName = $cookieStore.get('userName');
		//回到主页
		$scope.homePage = function() {
			$state.go('main');
		};
		//跳转到用户详细信息页面
		$scope.userInfo = function() {
			$state.go('user');
		};
		//用户登出，同时将cookie中用户信息删除
		$scope.logout = function() {
			console.log($cookieStore.get('isLoged'));
			$cookieStore.remove('isLoged');
			$cookieStore.remove('userName');
			$state.go('login');
		};
}]);
app.controller('contentController', ['$scope', '$cookieStore', '$rootScope', '$state',
	function($scope, $cookieStore, $rootScope, $state) {
		$scope.userName = $cookieStore.get('userName');
		var isLoged = $cookieStore.get('isLoged');
		if (typeof(isLoged) == 'undefined' || !isLoged) {
			$state.go('login');
		}
}]);
app.controller('userController', ['$scope', '$cookieStore', 'memoRequest', 'commonUtil',
    function($scope, $cookieStore, memoRequest, commonUtil) {
		var userName = $cookieStore.get('userName');
		$scope.userName = userName;
		//获取用户的详细信息
//		memoRequest.user.get({'userName': userName}, function(data) {
//			console.log(data)
//			commonUtil.setUser(data);
//		});
}]);
app.controller('userDetailController', ['$scope', '$cookieStore', '$state', 'memoRequest', 'commonUtil',
    function($scope, $cookieStore, $state, memoRequest, commonUtil) {
		var userName = $cookieStore.get('userName');
		//获取用户的详细信息
		memoRequest.user.get({'userName': userName}, function(data) {
			$scope.user = data;
		});
		//跳转到用户信息编辑页面
		$scope.goModify = function() {
			$state.go('user.modify');
		}
		//修改用户信息
		$scope.modify = function() {
			memoRequest.userModify.save($scope.user, function(data) {
				if (data.check) {
					$cookieStore.put('userName', $scope.user.userName);
					$state.go($state.current, {}, {reload: true});
				}
			})
		}
}]);
app.controller('passwordController', ['$scope', '$cookieStore', '$state', 'memoRequest',
    function($scope, $cookieStore, $state, memoRequest) {
		var userName = $cookieStore.get('userName');
		//修改密码
		$scope.change = function() {
			if ($scope.newPass === $scope.newPass2) {
				memoRequest.user.save(
					{userName: userName, oldPass: $scope.oldPass, newPass: $scope.newPass}, 
					function(data) {
						$scope.flag = !data.check;
						if (data.check) {
							$state.go('login');
							$cookieStore.remove('isLoged');
							$cookieStore.remove('userName');
						}
				});
			}
		};
}])
app.controller('mainController', ['$scope', 'mainService', '$state', 
    function($scope, mainService, $state) {
	//添加备忘
	$scope.add = function() {
		mainService.showAdd(function(data) {
			console.log(data)
			if (data) {
				$state.go($state.current, {}, {reload: true});
			}
		});
	};	
}]);
app.controller('listController', ['$stateParams', '$scope', 'mainService', 'memoRequest', 'commonUtil',
	function($stateParams, $scope, mainService, memoRequest, commonUtil) {
		var condition = $stateParams.condition;
		var status = $stateParams.status;
		//获取备忘
		memoRequest.memo.query({'status': status}, function(data) {
			commonUtil.setArr(data);
			$scope.memos = data;
		});
		//改变备忘的完成状态
		$scope.changeStatus = function(memoId) {
			memoRequest.modify.remove({'memoId': memoId}, function(date) {
				$scope.memos = commonUtil.modifyStatus(memoId, $stateParams.status);
			});
		};
		//删除备忘
		$scope.deleteMemo = function(memoId) {
			memoRequest.memo.remove({"memoId":memoId},function(){
				$scope.memos = commonUtil.deleteById(memoId);
			});
		};
		//修改备忘
		$scope.modify = function(memoId) {
			mainService.modify(memoId);
		}
}]);