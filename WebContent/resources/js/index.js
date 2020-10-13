/**
 *
 */
function allHide(){
	$("#info").hide();
	$("#choiseBetChip").hide();
	$("#choiseBigSmall").hide();
	$("#result").hide();
	$("#choiseDoubleUp").hide();
	$("#choiseContinue").hide();
	$("#gameOver").hide();
}

function formReset(){
	$("#betChip").val("");
	$('input:radio[name="bigOrSmall"]').val(["big"]);
	$('input:radio[name="doubleUp"]').val(["yes"]);
	$('input:radio[name="continue"]').val(["yes"]);
}

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

		var userInfoHtml = chip + "</br>" + "現在のカード: " + card;
		$("#userInfo").html(userInfoHtml);

		if(gameOver == true){
			var noChipHtml = "チップがなくなりました</br>";
			$("#noChip").html(noChipHtml);

			allHide();
			$("#result").show();
			$("#gameOver").show();

			// セッションストレージの削除
			sessionStorage.clear();
		}

		$("#info").show();
		sessionStorage.setItem("userChip", sumChip);
	});
}

function doBigOrSmall(param){
	$.ajax({
		contentType: "Content-Type: application/json; charset=UTF-8",
		url: "/BigOrSmall-web/BigOrSmall",
		type: "POST",
		data : JSON.stringify(param)

	}).done(function(data, status, xhr){
		$("#choiseBetChip").hide();
		$("#choiseBigSmall").hide();
		$("#result").show();

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

		var compareResultHtml = "";
		if(compareResult){
			compareResultHtml += "Win!! チップ" + getBet + "枚を獲得しました</br>"
											+ "[獲得したチップ" + getBet + "枚でBig or Smallを続けますか？]</br>";
			// DoubleUpの選択
			$("#choiseDoubleUp").show();
		}else{
			compareResultHtml += "Lose...</br>"
			// Continueの選択
			$("#choiseContinue").show();
		}

		$("#compareResult").html(compareResultHtml);

		// ユーザー情報の取得
		doInfo();
		// 入力フォームの初期化
		formReset();

	});
}

function doDoubleUp(param){
		$.ajax({
			contentType: "Content-Type: application/json; charset=UTF-8",
			url: "/BigOrSmall-web/DoubleUp",
			type: "POST",
			data : JSON.stringify(param)

		}).done(function(data, status, xhr){
			var doubleUp = data.doubleUp;

			if(doubleUp == "yes"){
				doInfo();
				$("#choiseDoubleUp").hide();
				$("#result").hide();
				$("#choiseBigSmall").show();

				sessionStorage.setItem("doubleUpFlg", true);
			}else{
				doInfo();
				$("#choiseDoubleUp").hide();
				$("#result").hide();
				$("#choiseContinue").show();

				sessionStorage.setItem("doubleUpFlg", false);
			}
		});

}

function doContinue(param){
	$.ajax({
		contentType: "Content-Type: application/json; charset=UTF-8",
		url: "/BigOrSmall-web/Continue",
		type: "POST",
		data : JSON.stringify(param)

	}).done(function(data, status, xhr){
		var exitFlg = data.exitFlg;

		if(exitFlg == false){
			doInfo();
			$("#result").hide();
			$("#choiseContinue").hide();
			$("#choiseBetChip").show();
			$("#choiseBigSmall").show();

			sessionStorage.setItem("doubleUpFlg", false);
		}else{
			$("#result").hide();
			$("#choiseContinue").hide();
			$("#gameOver").show();

			// セッションストレージの削除
			sessionStorage.clear();
		}
	});
}

// ページ読み込み時の処理
$(function(){
	allHide();

	// ベット枚数の選択
	$("#choiseBetChip").show();
	// Big or Smallの選択
	$("#choiseBigSmall").show();
	// 情報を読み込む
	doInfo();
	// ダブルアップしてない状態をセット
	sessionStorage.setItem("doubleUpFlg", false);

	// BET枚数、Big or Smallの確定時
	$("#submitBigOrSmall").click(function(){
		var betChip = Number($("#betChip").val());
		var userChip = Number(sessionStorage.getItem("userChip"));
		var doubleUpFlg = sessionStorage.getItem("doubleUpFlg");

		var betErrorMsgHtml = "";

		// BETするチップが1-20またはダブルアップの場合
		if(doubleUpFlg == "true" || 0 < betChip && betChip <= 20){
			// BETするチップが所持チップ以内の場合
			if(userChip >= betChip){
				var param = {
					betChip: betChip,
					bigOrSmall: $("input[name=bigOrSmall]:checked").val()
				};
				doBigOrSmall(param);

			// BETするチップが所持チップ以上の場合
			}else{
				betErrorMsgHtml = "所持枚数以上のチップはBETできません</br>";
			}
		// BETするチップが範囲外の場合
		}else{
			betErrorMsgHtml = "BETするチップ枚数は最低1〜最大20の範囲で入力してください</br>";
		}
		$("#betErrorMsg").html(betErrorMsgHtml);
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