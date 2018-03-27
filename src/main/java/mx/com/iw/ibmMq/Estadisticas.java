package mx.com.iw.ibmMq;

import java.io.IOException;

import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQCFC;
import com.ibm.mq.headers.CMQC;
import com.ibm.mq.headers.pcf.CMQXC;
import com.ibm.mq.pcf.PCFMessage;
import com.ibm.mq.pcf.PCFMessageAgent;




public class Estadisticas {
	private static final String qMngrStr = "Q1Q";
	private static final String user = "fvalencia";
	private static final String password = "12345678";
	private static final String queueName = "prueba";
	private static final String hostName = "10.3.2.178";
	private static final String port = "1444";
	private static final String channel = "SYSTEM.DEF.SVRCONN";
	// Create a default local queue.
	MQQueueManager qManager;

	
	public void Datos(String qMngrStr,String queueName ,String hostName, String port,String channel,String user ) throws IOException {

		// Set MQ connection credentials to MQ Envorinment.
		MQEnvironment.hostname = hostName;
		MQEnvironment.channel = channel;
		MQEnvironment.port = Integer.parseInt(port);
		MQEnvironment.userID = user;
		//MQEnvironment.password = password;
		
		try {
			//
			qManager = new MQQueueManager(qMngrStr);
			
		    PCFMessageAgent agent = new PCFMessageAgent(qMngrStr);
			
		   PCFMessage inquireQueueStatus = new PCFMessage(CMQCFC.MQCMD_INQUIRE_Q_STATUS);

			inquireQueueStatus.addParameter(CMQC.MQCA_Q_NAME, queueName);
			inquireQueueStatus.addParameter(CMQCFC.MQIACF_Q_STATUS_TYPE | CMQCFC.MQIACH_CHANNEL_TYPE,
					new int[] { CMQCFC.MQIACF_Q_STATUS, CMQXC.MQCHT_ALL });
			
			// TODO: Caso de exemplo com mais dados que podem ser obtidos
			inquireQueueStatus.addParameter(CMQCFC.MQIACF_Q_STATUS_ATTRS | CMQCFC.MQIACH_CHANNEL_INSTANCE_ATTRS,
					new int[] { CMQC.MQCA_Q_NAME, CMQC.MQIA_CURRENT_Q_DEPTH, CMQCFC.MQCACF_LAST_GET_DATE,
							CMQCFC.MQCACF_LAST_GET_TIME, CMQCFC.MQCACF_LAST_PUT_DATE, CMQCFC.MQCACF_LAST_PUT_TIME,
							CMQCFC.MQIACF_OLDEST_MSG_AGE, CMQC.MQIA_OPEN_INPUT_COUNT, CMQC.MQIA_OPEN_OUTPUT_COUNT,
							CMQCFC.MQIACF_UNCOMMITTED_MSGS, CMQCFC.MQCACH_CHANNEL_NAME,
							CMQCFC.MQIACF_Q_TIME_INDICATOR });

			 PCFMessage[] responses = agent.send(inquireQueueStatus);
			for (PCFMessage pcfMessage : responses) {

				String name = (String) pcfMessage.getParameterValue(CMQC.MQCA_Q_NAME);
				System.out.println("Queue: " + name);
				System.out
						.println("Profundidad de la queue: " + pcfMessage.getParameterValue(CMQC.MQIA_CURRENT_Q_DEPTH));
				System.out.println(
						"Numero de entradas abiertas: " + pcfMessage.getParameterValue(CMQC.MQIA_OPEN_INPUT_COUNT));
				System.out.println(
						"Fecha del último trasferencia: " + pcfMessage.getParameterValue(CMQCFC.MQCACF_LAST_PUT_DATE));
				System.out.println(
						"Hora del último trasferencia: " + pcfMessage.getParameterValue(CMQCFC.MQCACF_LAST_PUT_TIME));
				System.out.println(
						"Numero de salidas abiertas: " + pcfMessage.getParameterValue(CMQC.MQIA_OPEN_OUTPUT_COUNT));
				System.out.println(
						"Fecha de la último obtención: " + pcfMessage.getParameterValue(CMQCFC.MQCACF_LAST_GET_DATE));
				System.out.println(
						"Hora de la último obtención: " + pcfMessage.getParameterValue(CMQCFC.MQCACF_LAST_GET_TIME));
				System.out.println("Tiempo del mensaje mas antiguo: "
						+ pcfMessage.getParameterValue(CMQCFC.MQIACF_OLDEST_MSG_AGE));
			}

		} catch (MQException e) {
			e.printStackTrace();
		}
	}

	public static void main(String... args) throws IOException {
		
		if (args != null && args.length > 0) {
			System.out.println("Processing Main...");

			Estadisticas clientTest = new Estadisticas();

			// initialize MQ.
			clientTest.Datos(args[0], args[1], args[2], args[3], args[4], args[5]);

			System.out.println("Done!");

			
		} else {
			System.out.println("Faltan argumentos");
		}

		
	}


}
