isRankmostAvailable = pcall(function()require("rankmost")end)
--The first step is to initialize your Rankmost SDK with your application guid in order to use other methods.
if(isRankmostAvailable) then
	rankmost:initialize("<applicationGuid>");
else
	print("rankmost not available");
end

local label = TextField.new(nil, "Scoreboard")
label:setPosition(40, 80)
label:setScale(2)
label:setScale(2)
stage:addChild(label)

if(isRankmostAvailable) then
	label:addEventListener(Event.TOUCHES_BEGIN,
	function(e)
		if label:hitTestPoint(e.touch.x, e.touch.y) then
		-- This method is called to open the Portal as a webview.
		-- After logging in, the user is able to see their scoreboards and trophies.
		-- To open different pages such as  scoreboard, forum, etc., call this method with constants which you can find from Rankmost SDK
		-- After logging in, the user is able to see their scoreboards and trophies. 
		rankmost:startPortal(<portalPage>);
		end
	end)	
end

local text = TextField.new(nil, "Send Score")
text:setPosition(40, 160)
stage:addChild(text)
if(isRankmostAvailable) then
	text:addEventListener(Event.TOUCHES_BEGIN,
	function(e)
		if text:hitTestPoint(e.touch.x, e.touch.y) then
		--This method is called to send the user's score with the parameters leaderboard guid and score.
		--Score is a number, guid is string
			rankmost:sendScore("<leaderboardGuid>", <score>);
			--Handle this event to get a response for the request. 
			--Two args; status and message
			rankmost:addEventListener(Event.SCORE_SENT, 
			function(e)
				local score_response = TextField.new(nil, "STATUS: "..e.status.." Value "..e.recordBreak.."  MSG: "..e.message)
				score_response:setPosition(20, 500)
				score_response:setScale(2)
				stage:addChild(score_response)
			end)
		end
	end)
end

local text2 = TextField.new(nil, "Send Trophy")
text2:setPosition(40, 240)
text2:setScale(2)
stage:addChild(text2)
if(isRankmostAvailable) then
	text2:addEventListener(Event.TOUCHES_BEGIN, 
	function(e)
		if text2:hitTestPoint(e.touch.x, e.touch.y) then
		--This method is called with trophy guid in order to send the user's achievement of 100%
		rankmost:sendTrophy("<trophyGuid>"); --Full completition
			
		end
	end)
end

local text3 = TextField.new(nil, "Send Trophy with %")
text3:setPosition(40, 320)
text3:setScale(2)
stage:addChild(text3)
if(isRankmostAvailable) then
	text3:addEventListener(Event.TOUCHES_BEGIN, 
	function(e)
		if text3:hitTestPoint(e.touch.x, e.touch.y) then
		--This method is called with trophy guid and percentage in order to send the user's partial achievement.
		--Each time this method is called, the new value will be added to the previously earned percentage value.
		rankmost:sendTrophy("<trophyGuid>", <percentage>); 
		end
	end)
	--Handle this event to get a response for the request. 
	--Three args; status, value  and message
	--value is a completion percentage
	rankmost:addEventListener(Event.TROPHY_SENT, 
	function(e)
		local trophy_response = TextField.new(nil, "STATUS: "..e.status.."  CMP: "..e.completitionValue.."  MSG: "..e.message)
		trophy_response:setPosition(20, 600)
		trophy_response:setScale(2)
		stage:addChild(trophy_response)
		end)
end

local label = TextField.new(nil, "ScoreboardWLG")
label:setPosition(40, 400)
label:setScale(2)
stage:addChild(label)

if(isRankmostAvailable) then
	label:addEventListener(Event.TOUCHES_BEGIN,
	function(e)
		if label:hitTestPoint(e.touch.x, e.touch.y) then
		-- This method opens the High Score Ranking page by calling it with leaderboardguid
			rankmost:startPortalWithLeaderBoard("<leaderboardGuid>");
		end
	end)	
end
