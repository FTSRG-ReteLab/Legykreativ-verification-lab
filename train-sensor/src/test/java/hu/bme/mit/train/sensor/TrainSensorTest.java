package hu.bme.mit.train.sensor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainUser;
import hu.bme.mit.train.user.TrainUserImpl;

import static org.mockito.Mockito.*;

public class TrainSensorTest {
	
	TrainSensorImpl ts;
	TrainUserImpl mockTUI;
	TrainController mockTC;
	
    @Before
    public void init() {
        mockTUI = mock(TrainUserImpl.class);
        mockTC = mock(TrainController.class);
         ts= new TrainSensorImpl(mockTC,mockTUI);
    }

    @Test
    public void overrideSpeedLimit_smallerThanMin_GetAlarm() {
        ts.overrideSpeedLimit(-1);
        verify(mockTUI, times(1)).setAlarmState(true);
    }
    @Test
    public void overrideSpeedLimit_greaterThanMax_GetAlarm() {
        ts.overrideSpeedLimit(501);
        verify(mockTUI, times(1)).setAlarmState(true);
    }
    @Test
    public void overrideSpeedLimit_tooBigChange_GetAlarm() {
    	when(mockTC.getReferenceSpeed()).thenReturn(150);
        ts.overrideSpeedLimit(50);
        verify(mockTUI, times(1)).setAlarmState(true);
    }
    @Test
    public void overrideSpeedLimit_correctChange_NoAlarm() {
    	when(mockTC.getReferenceSpeed()).thenReturn(100);
        ts.overrideSpeedLimit(120);
        verify(mockTUI, times(1)).setAlarmState(false);
    }
}
