/**
 * Created by prageeth.g on 27/10/2016.
 */
var model = angular.module('myMainAPP', ['ngRoute']);
model.config(function ($routeProvider) {
    $routeProvider.
        when('/', {
            templateUrl: '/main.html',
            controller: 'myController'
        })
        .when('/mainPage', {
            resolve: {"check": authCheck},
            templateUrl: '/mainPage.html',
            controller: 'toolBarController'
        })
        .when('/createBranch', {
            resolve: {"check": authCheck},
            templateUrl: '/addBranch.html',
            controller: 'createBranchController'
        }).
        otherwise({
            redirectTo: '/main.html'
        });
})

model.controller("myController", function ($scope, $location, $log, $http, $rootScope) {
    $log.info($location.path());

    var successfullyLogin = function (response) {
        $log.info(response.data);
        $location.path("/mainPage");

    }

    var errorInLogin = function (response) {
        $log.info(response.data);
    }

    $scope.loginSubmit = function () {
        var username = $scope.userName;
        var password = $scope.password;
        $rootScope.userName = username;
        $rootScope.password = password;
        $log.info($scope.userName);
        $http.get('http://localhost:8080/login?userName=' + username + '&password=' + password)
            .then(successfullyLogin, errorInLogin);
    }
});

model.controller("toolBarController", function ($scope, $location, $log) {
    $log.info($location.path());
    $scope.addBranch = function () {
        $log.info('Create Branch');
        $log.info($scope.userName);
        $log.info($scope.password);
        $location.path('/createBranch');
    };
    $scope.addGroup = function () {
        $log.info('Create Group');
    };
    $scope.addClient = function () {
        $log.info('Create Client');
    };
    $scope.addAccount = function () {
        $log.info('Create Account');
    };
    $scope.addPayment = function () {
        $log.info('Create Payment');
    };
});

model.controller("createBranchController", function ($scope, $location, $log, $http, $rootScope) {

    $log.info($location.path());
    var successfullyLogin = function (response) {
        $log.info(response.data);
        $location.path('/mainPage');
    }

    var errorInLogin = function (response) {
        $log.info(response.data);
    }

    $scope.createBranch = function () {
        var entityContext = {"entityType": "Branch",
            "entity": {
                "id": 0,
                "branchName": $scope.branchName},
            "securityContext": {"user": {"id": "0" ,
                "userName": $rootScope.userName,
                "password": $rootScope.password,
                "authorized": false}}}
        $log.info(entityContext);
        $http.post('http://localhost:8080/createBranch', entityContext)
            .then(successfullyLogin, errorInLogin);
    }
});

var authCheck = function ($log, $location, $rootScope) {
    $log.info($rootScope.userName);
    if (!$rootScope.userName || !$rootScope.password || $rootScope.userName.length == 0 ||
        $rootScope.password.length == 0) {
        $location.path("/");
    }
}

//model.value('clientId', { value: 'a12345654321x' });


