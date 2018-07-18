var fnObj = {};
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: ["samples", "parent"],
            data: caller.searchView.getData(),
            callback: function (res) {
                caller.gridView01.setData(res);
            },
            options: {
                // axboot.ajax 함수에 2번째 인자는 필수가 아닙니다. ajax의 옵션을 전달하고자 할때 사용합니다.
                onError: function (err) {
                    console.log(err);
                }
            }
        });

        return false;
    },
    PAGE_SAVE: function (caller, act, data) {
        var saveList = [].concat(caller.gridView01.getData("modified"));
        saveList = saveList.concat(caller.gridView01.getData("deleted"));

        axboot.ajax({
            type: "PUT",
            url: ["samples", "parent"],
            data: JSON.stringify(saveList),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                axToast.push("저장 되었습니다");
            }
        });
    },
    ITEM_CLICK: function (caller, act, data) {

    },
    ITEM_ADD: function (caller, act, data) {
        caller.gridView01.addRow();
    },
    ITEM_DEL: function (caller, act, data) {
        caller.gridView01.delRow("selected");
    },
    dispatch: function (caller, act, data) {
        var result = ACTIONS.exec(caller, act, data);
        if (result != "error") {
            return result;
        } else {
            // 직접코딩
            return false;
        }
    }
});

// fnObj 기본 함수 스타트와 리사이즈
fnObj.pageStart = function () {

    $('#calendar').fullCalendar({
        header: {
            left: 'prev, next',
            center: 'title',
            right: 'month, listMonth'
        },
        defaultDate: '2017-02-12',
        contentHeight: 540,
        locale: 'ko',
        buttonIcons: true, // show the prev/next text
        weekNumbers: false,
        navLinks: true, // can click day/week names to navigate views
        editable: true,
        eventLimit: true, // allow "more" link when too many events
        eventDrop: function(event, delta, revertFunc) {
            console.log('event: ', event);
            console.log('delta: ', delta);
            alert("[드래그 이벤트]  TX ID :: " + event.txId + " 타이틀 :: " +  event.title + "  일자 :: " + event.start.format());
        },
        eventClick: function(event, element) {
            alert("[클릭 이벤트]  TX ID :: " + event.txId + " 타이틀 :: " +  event.title + "  일자 :: " + event.start.format());
        }

        /*events: [
            {
                title: 'All Day Event',
                start: '2017-02-01'
            },
            {
                title: 'Long Event',
                start: '2017-02-07',
                end: '2017-02-10'
            },
            {
                id: 999,
                title: 'Repeating Event',
                start: '2017-02-09T16:00:00'
            },
            {
                id: 999,
                title: 'Repeating Event',
                start: '2017-02-16T16:00:00'
            },
            {
                title: '현송계획',
                start: '2017-02-11'
            },
            {
                title: 'Meeting',
                start: '2017-02-12T10:30:00',
                end: '2017-02-12T12:30:00'
            },
            {
                title: 'Lunch',
                start: '2017-02-12T12:00:00'
            },
            {
                title: 'Meeting',
                start: '2017-02-12T14:30:00'
            },
            {
                title: 'Happy Hour',
                start: '2017-02-12T17:30:00'
            },
            {
                title: 'Dinner',
                start: '2017-02-12T20:00:00'
            },
            {
                title: 'Birthday Party',
                start: '2017-02-13T07:00:00'
            },
            {
                title: 'Click for Google',
                url: 'http://google.com/',
                start: '2017-02-28'
            }
        ]*/
    });

};

fnObj.pageResize = function () {

};


fnObj.pageButtonView = axboot.viewExtend({
    initView: function () {
        axboot.buttonClick(this, "data-page-btn", {
            "search": function () {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            },
            "save": function () {
                ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
            },
            "add": function () {
            }
        });
    }
});

//== view 시작
/**
 * searchView
 */
fnObj.searchView = axboot.viewExtend(axboot.searchView, {
    initView: function () {
        this.target = $(document["searchView0"]);
        this.target.attr("onsubmit", "return ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);");
        this.filter = $("#filter");
    },
    getData: function () {
        return {
            filter: this.filter.val()
        }
    }
});

$('#sample').on('click', function () {

    var events = [
        {
            title: '현송계획',
            start: '2017-02-11',
            txId: '1234567'
        },
        {
            title: 'Meeting',
            start: '2017-02-12T10:30:00',
            end: '2017-02-12T12:30:00',
            txId: '222222'
        }
    ];

    $('#calendar').fullCalendar('addEventSource', events);

});

$('#add').on('click', function () {

    var event = {}, events = [];

    event.title = $('#title').val();
    event.start = $('#datetime').val().substr(0,4) + "-" + $('#datetime').val().substr(4,2) + "-" + $('#datetime').val().substr(6,2);

    events.push(event);

    $('#calendar').fullCalendar('addEventSource', events);

});