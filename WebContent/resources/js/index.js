/**
 * 初期化処理
 */
function init(){
	allHide();

	// Big or Small選択画面を表示
	$("#choiseBetChip").show();
	$("#choiseBigSmall").show();

	// ユーザー情報画面の更新
	doInfo();

	// セッションストレージにダブルアップフラグを保存
	sessionStorage.setItem("doubleUpFlg", false);
}


/**
 * すべてのbody要素を非表示
 */
function allHide(){
	$("#info").hide();
	$("#choiseBetChip").hide();
	$("#choiseBigSmall").hide();
	$("#result").hide();
	$("#choiseDoubleUp").hide();
	$("#choiseContinue").hide();
	$("#gameOver").hide();
	$("#congraturation").hide();
}

/**
 * フォームの入力情報をリセット
 */
function formReset(){
	$("#betChip").val("");
	$('input:radio[name="bigOrSmall"]').val(["big"]);
	$('input:radio[name="doubleUp"]').val(["yes"]);
	$('input:radio[name="continue"]').val(["yes"]);

	// エラーメッセージの削除
	$("#betErrorMsg").html("");
}

/**
 * ユーザー情報の表示
 */
function doInfo(){
	$.ajax({
		contentType: "Content-Type: application/json; charset=UTF-8",
		url: "/BigOrSmall-web/Info",
		type: "GET"

	}).done(function(data, status, xhr){
		var chip = data.chip;
		var sumChip = data.sumChip;
		var card = data.card;
		var gameOver = data.gameOver;
		var gameClear = data.gameClear;

		var userInfoHtml = chip + "</br>" + "現在のカード: " + card;
		$("#userInfo").html(userInfoHtml);

		// ユーザー情報画面を表示
		$("#info").show();
		// セッションストレージに合計チップ数を保存
		sessionStorage.setItem("userChip", sumChip);

		// ゲームオーバー時の処理
		if(gameOver == true){
			var noChipHtml = "チップがなくなりました</br>";
			$("#noChip").html(noChipHtml);

			// ゲームオーバー画面を表示
			allHide();
			$("#result").show();
			$("#gameOver").show();

			// セッションストレージの削除
			sessionStorage.clear();
		}

		// ゲームクリア時の処理
		if(gameClear == true){
			// ゲームクリア画面を表示
			allHide();
			$("#congraturation").show();

			// セッションストレージの削除
			sessionStorage.clear();
		 }
	});
}

/**
 * Big or Smallの判定
 */
function doBigOrSmall(param){
	$.ajax({
		contentType: "Content-Type: application/json; charset=UTF-8",
		url: "/BigOrSmall-web/BigOrSmall",
		type: "POST",
		data : JSON.stringify(param)

	}).done(function(data, status, xhr){
		var compareResult = data.compareResult;
		var bet = data.bet;
		var getBet = data.getBet;
		var bigSmall = data.bigSmall;
		var prev = data.prev;
		var follow = data.follow;

		var resultInfoHtml = "BET数: " + bet + "</br>"
									+ "あなたの選択: " + bigSmall + "</br>"
									+ "現在のカード: " + prev + "</br>"
									+ "引いたカード: " + follow + "</br>";

		$("#resultInfo").html(resultInfoHtml);

		// 判定結果画面を表示
		$("#choiseBetChip").hide();
		$("#choiseBigSmall").hide();
		$("#result").show();

		// 勝ったときの処理
		var compareResultHtml = "";
		if(compareResult){
			compareResultHtml += "Win!! チップ" + getBet + "枚を獲得しました</br>"
											+ "[獲得したチップ" + getBet + "枚でBig or Smallを続けますか？]</br>";

			// DoubleUp選択画面の表示
			$("#choiseDoubleUp").show();

		// 負けたときの処理
		}else{
			compareResultHtml += "Lose...</br>"

			// Continue選択画面の表示
			$("#choiseContinue").show();
		}

		$("#compareResult").html(compareResultHtml);

		// ユーザー情報画面の更新
		doInfo();
		// 入力フォームの初期化
		formReset();

	});
}

/**
 * DoubleUpの選択
 */
function doDoubleUp(param){
		$.ajax({
			contentType: "Content-Type: application/json; charset=UTF-8",
			url: "/BigOrSmall-web/DoubleUp",
			type: "POST",
			data : JSON.stringify(param)

		}).done(function(data, status, xhr){
			var doubleUp = data.doubleUp;

			// ダブルアップするときの処理
			if(doubleUp == "yes"){
				// ユーザー情報画面の更新
				doInfo();

				// Big or Small選択画面を表示
				$("#choiseDoubleUp").hide();
				$("#result").hide();
				$("#choiseBigSmall").show();

				// セッションストレージにダブルアップフラグを保存
				sessionStorage.setItem("doubleUpFlg", true);

			// ダブルアップしないときの処理
			}else{
				// ユーザー情報画面の更新
				doInfo();

				// ゲーム継続確認画面を表示
				$("#choiseDoubleUp").hide();
				$("#result").hide();
				$("#choiseContinue").show();

				// セッションストレージにダブルアップフラグを保存
				sessionStorage.setItem("doubleUpFlg", false);
			}
		});

}

/**
 * ゲーム継続の確認
 */
function doContinue(param){
	$.ajax({
		contentType: "Content-Type: application/json; charset=UTF-8",
		url: "/BigOrSmall-web/Continue",
		type: "POST",
		data : JSON.stringify(param)

	}).done(function(data, status, xhr){
		var continueFlg = data.continueFlg;

		// ゲーム継続するときの処理
		if(continueFlg == true){
			// ユーザー情報画面の更新
			doInfo();

			// Big or Small選択画面を表示
			$("#result").hide();
			$("#choiseContinue").hide();
			$("#choiseBetChip").show();
			$("#choiseBigSmall").show();

			// セッションストレージにダブルアップフラグを保存
			sessionStorage.setItem("doubleUpFlg", false);

		// ゲーム継続しないときの処理
		}else{
			// ゲームオーバー画面を表示
			$("#result").hide();
			$("#choiseContinue").hide();
			$("#gameOver").show();

			// セッションストレージの削除
			sessionStorage.clear();
		}
	});
}

/**
 * 入力のチェック
 */
function inputCheck(betChip, userChip, doubleUpFlg){
	var checkResult = false;
	var betErrorMsgHtml = "";

	// BETするチップが1-20またはダブルアップの場合
	if(doubleUpFlg == "true" || 0 < betChip && betChip <= 20){
		// BETするチップが所持チップ以内の場合
		if(userChip >= betChip){
			return true;

		// BETするチップが所持チップ以上の場合
		}else{
			betErrorMsgHtml = "所持枚数以上のチップはBETできません</br>";
		}
	// BETするチップが範囲外の場合
	}else{
		betErrorMsgHtml = "BETするチップ枚数は最低1〜最大20の範囲で入力してください</br>";
	}

	$("#betErrorMsg").html(betErrorMsgHtml);
	return false;
}

// ページ読み込み時の処理
$(function(){
	init();

	// BET枚数、Big or Smallの確定時
	$("#submitBigOrSmall").click(function(){
		var betChip = Number($("#betChip").val());
		var userChip = Number(sessionStorage.getItem("userChip"));
		var doubleUpFlg = sessionStorage.getItem("doubleUpFlg");

		// 入力のチェック
		var checkResult = inputCheck(betChip, userChip, doubleUpFlg);

		if(checkResult){
			var param = {
				betChip: betChip,
				bigOrSmall: $("input[name=bigOrSmall]:checked").val()
			};
			doBigOrSmall(param);
		}
	});

	// ダブルアップの確定時
	$("#submitDoubleUp").click(function(){
		var param = {
			doubleUp : $("input[name=doubleUp]:checked").val()
		};
		doDoubleUp(param);
	});

	// ゲーム継続の確定時
	$("#submitContinue").click(function(){
		var param = {
			continue: $("input[name=continue]:checked").val()
		};
		doContinue(param);
	});
});