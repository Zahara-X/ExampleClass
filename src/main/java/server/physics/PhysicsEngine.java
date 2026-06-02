package server.physics;

import server.entity_server.CubeServer;

public class PhysicsEngine {
     private final CubeServer cubeServer;
     public PhysicsEngine(CubeServer cubeServer) {
         this.cubeServer = cubeServer;
     }
     public void updatePhysics() {
         if(cubeServer.channel == null) return;
         int keys = cubeServer.currentMask;
         if((keys & (1 << 0)) != 0) cubeServer.move('W');
         if((keys & (1 << 1)) != 0) cubeServer.move('S');
         if((keys & (1 << 2)) != 0) cubeServer.move('A');
         if((keys & (1 << 3)) != 0) cubeServer.move('D');
     }
}