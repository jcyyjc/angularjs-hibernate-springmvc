var appService = angular.module('memoService',['ui.router', 'ui.bootstrap', 'ngResource']);
appService.factory('loginService', function() {
	return {
		
	}
});
appService.factory('authService', function($http) {
	return {
		isLoged: false,
		userName: '',
		getUserInfo: function(data) {
			var promise = $http.put('memo/logins',data).then(function(response) {
				return response.data;
			});
			return promise;
		}
	}
});
appService.factory('mainService', function($modal, $resource, $cookieStore, memoRequest, commonUtil) {
	return {
		//添加待办备忘
		showAdd: function(callback) {
			$modal.open({
				templateUrl: 'html/add.html',
				controller: function($scope, $modalInstance) {
					$scope.cancel = function() {
						$modalInstance.dismiss('cancel');
					}
					$scope.addMemo = function(memo) {
						if (typeof(memo) != 'undefined' && typeof(memo.content) != 'undefined') {
							memo.userName = $cookieStore.get('userName');
							memo.importance = commonUtil.switchParam(memo.importance);
							memo.reminder = commonUtil.switchParam(memo.reminder);
							memoRequest.memo.save(memo, function(data) {
								callback(true);
							});
							$modalInstance.close();
						}
					};
				}
			});
		},
		//修改待办备忘
		modify: function(memoId) {
			$modal.open({
				templateUrl: 'html/add.html',
				controller: function($scope, $modalInstance) {
					var memo = commonUtil.getById(commonUtil.getArr(), memoId);
					if (memo.importance === 1 || memo.importance === 0) {
						memo.importance = commonUtil.switchParam(memo.importance);
					}
					if (memo.reminder === 1 || memo.reminder === 0) {
						memo.reminder = commonUtil.switchParam(memo.reminder);
					}
					$scope.memo = memo;
					$scope.cancel = function() {
						$modalInstance.dismiss('cancel');
					};
					$scope.addMemo = function() {
						if (typeof(memo) != 'undefined' && typeof(memo.content) != 'undefined') {
							memo.importance = commonUtil.switchParam(memo.importance);
							memo.reminder = commonUtil.switchParam(memo.reminder);
							memoRequest.modify.save(memo,function(date){
								
							});
							$modalInstance.close();
						}
					};
				}
			});
		}
	}
});
appService.factory('memoRequest',function($resource) {
	return {
		memo: $resource('memo/:memoId/:status', {}, {
			'save': {method: 'PUT'}
		}),
		modify: $resource('memo/modify/:memoId', {}, {
			'save': {method: 'PUT'}
		}),
		user: $resource('memo/user/:userName', {}, {
			'save': {method: 'PUT'}
		}),
		userModify: $resource('memo/user/modify', {}, {
			'save': {method: 'PUT'}
		}),
		regisiter: $resource('memo/regisiter', {}, {
			'save': {method: 'PUT'}
		})
	}
});
appService.factory('commonUtil', function() {
	var arr = [];
	return {
		getById: function(arr, id) {
		    for (var i = 0; i < arr.length; i++) {
		        if (arr[i].id == id) return arr[i];
		    }
		    return null;
		},
		setArr: function(memoList) {
			arr = memoList;
		},
		getArr: function() {
			var arrs = new Array();
			arrs = arr
			return arrs;
		},
		modifyStatus: function (memoId, status) {
			angular.forEach(arr, function(value) {
				if (value.id === memoId) {
					if (status == 0 || status == 1) {
						var index = arr.indexOf(value);
						arr.splice(index, 1);
					} else {
						value.status = 1;
					}
				}
			});
			return arr;
		},
		deleteById: function(memoId) {
			angular.forEach(arr, function(value) {
				if (value.id == memoId) {
					var index = arr.indexOf(value);
					arr.splice(index, 1);
				}
			});
			return arr;
		},
		switchParam: function(param) {
			if (param === true) {
				return 1;
			}
			if (param === 1) {
				return true;
			}
			if (param === false) {
				return 0;
			}
			if (param === 0) {
				return false;
			}
			if (typeof(param) == 'undefined') {
				return 0;
			}
		}
	}
});