# spring app 실행
cd ~/todo-max/be # be 디렉토리로 이동
sudo chmod +x ./gradlew # gradlew에 실행 권한 부여
./gradlew clean build -x test # 테스트 없이 빌드
cd build/libs/ # build/libs/ 디렉토리로 이동
nohup java -Dspring.profiles.active=prod -jar *.jar > ~/log.txt 2>&1 & # prod 프로파일로 스프링 실행, 로그는 ~/log.txt에 저장, 백그라운드로 실행

# react web 실행
cd ~/todo-max/frontend
npm install # node_modules
npm run build # 리액트 빌드
npm install -g serve # servce 설치
nohup serve -s build > ~/fe-log.txt 2>&1 & # 백그라운드 실행
