<?php																	
			include_once "alfa.sms.class.php";									
			$sms=new AlfaSMS();													
			$result=$sms->login('9964534673','asilutr');							
			$sms->send('9964534673','Thanks Alfred,your API works perfectly.');	
			$sms->logout();															
		?>	
